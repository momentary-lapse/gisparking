/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Geocoder;
import models.ImageTransformer;
import models.entities.Ban;
import models.entities.Marker;
import models.entities.Phone;
import models.services.BanService;
import models.services.MarkerService;
import models.services.PhoneService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class IndexController {

    
    final double chelLat = 55.17;
    final double chelLng = 61.388115;
    final int maxBadQueries = 5;
    final int maxQueriesPerDay = 25;
    final int maxBans = 5;
    final long daysOfBan = 30;
    final long timeToBan = 1000 * 60 * 60 * 24 * daysOfBan;
    @Autowired
    MarkerService markerService;
    @Autowired
    PhoneService phoneService;
    @Autowired
    BanService banService;

    @RequestMapping(value = "/")
    public String go() {
        return "redirect:start";
    }

    @RequestMapping(value = "/start/{id}", method = RequestMethod.GET)
    public String indexGetById(@RequestParam(value="lat", required=false) Double lat, @RequestParam(value="lng", required=false) Double lng, @PathVariable Long id, HttpServletRequest request, ModelMap model) {

        if (lat == null || lng == null) {
            lat = chelLat;
            lng = chelLng;
        }
        model.addAttribute("marker", markerService.getById(id));
        model.addAttribute("lat", lat.doubleValue());
        model.addAttribute("lng", lng.doubleValue());
        
        return "index";

    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String indexGet(@RequestParam(value="lat", required=false) Double lat, @RequestParam(value="lng", required=false) Double lng, ModelMap model) {


        if (lat == null || lng == null) {
            lat = chelLat;
            lng = chelLng;
        }
        model.addAttribute("markers", markerService.getClusterizedList(lat, lng));
        model.addAttribute("lat", lat.doubleValue());
        model.addAttribute("lng", lng.doubleValue());
        
        return "index";

    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String markerGetById(@PathVariable Long id, ModelMap model) {

        Marker m = markerService.getById(id);
        
        JSONObject marker = new JSONObject();
        marker.put("id", m.getId());
        marker.put("lat", m.getLat());
        marker.put("lng", m.getLng());
        marker.put("address", m.getAddress());
        marker.put("phone", phoneService.getById(m.getPid()).getPhone());

        model.addAttribute("marker", marker.toString());
        return "marker";

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String markerDeleteById(@PathVariable Long id, ModelMap model) {

        markerService.deleteById(id);

        return "redirect:/result/success";

    }

    @RequestMapping(value = "/choose/{id}", method = RequestMethod.GET)
    public String markerChoose(@PathVariable Long id, ModelMap model) {

        Marker m = markerService.getById(id);
        if (!m.getEnabled()) {
            return "redirect:/result/busy";
        }
        m.setEnabled(false);
        markerService.update(m);

        return "redirect:/result/success";

    }

    @RequestMapping(value = "/complain/{id}", method = RequestMethod.GET)
    public String complainPhone(@PathVariable Long id, ModelMap model) {

        Marker m = markerService.getById(id);
        Phone p = phoneService.getById(m.getPid());

        if (p == null) {

            return "redirect:/result/unknown";

        }

        p.setPriority(p.getPriority() + 1);

        phoneService.update(p);

        if (p.getPriority() >= maxBadQueries) {
            Ban b = banService.getByPhone(p.getId());
            if (b == null) {
                b = new Ban();
                b.setPid(p.getId());
                b.setStamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                b.setNumber(1);
                banService.add(b);
            }
            if (b.getStamp().before(new Timestamp(Calendar.getInstance().getTimeInMillis() - timeToBan))) {
                b.setStamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                b.setNumber(b.getNumber() + 1);
                banService.update(b);
            }

        }

        return "redirect:/delete/" + id.toString();

    }

    @RequestMapping(value = "/cancel/{id}", method = RequestMethod.GET)
    public String markerCancel(@PathVariable Long id, ModelMap model) {

        Marker m = markerService.getById(id);
        if (m == null) {
            return "redirect:/result/unknown";
        }
        m.setEnabled(true);
        markerService.update(m);

        return "redirect:/result/success";

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listGet(ModelMap model) {

        List<Marker> list = markerService.getEnabledList();

        JSONArray markers = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject marker = new JSONObject();
            marker.put("id", list.get(i).getId());
            marker.put("lat", list.get(i).getLat());
            marker.put("lng", list.get(i).getLng());
            marker.put("address", list.get(i).getAddress());
            marker.put("phone", phoneService.getById(list.get(i).getPid()).getPhone());
            markers.add(marker);
        }
        JSONObject result = new JSONObject();
        result.put("markers", markers);

        model.addAttribute("list", result.toString());

        return "list";

    }

    @RequestMapping(value = "/geolist", method = RequestMethod.GET)
    public String geoListGet(@RequestParam("lat") double lat, @RequestParam("lng") double lng, ModelMap model) {

        List<Marker> list = markerService.getSortedList(lat, lng);

        JSONArray markers = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONObject marker = new JSONObject();
            marker.put("id", list.get(i).getId());
            marker.put("lat", list.get(i).getLat());
            marker.put("lng", list.get(i).getLng());
            marker.put("address", list.get(i).getAddress());
            marker.put("phone", phoneService.getById(list.get(i).getPid()).getPhone());
            markers.add(marker);
        }
        JSONObject result = new JSONObject();
        result.put("markers", markers);

        model.addAttribute("list", result.toString());

        return "list";

    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getForm(ModelMap model) {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String sendData(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng, @RequestParam("phone") String phone, @RequestParam("image") MultipartFile file, HttpServletRequest request, ModelMap model) {

        if (!"image/jpg".equals(file.getContentType()) && !"image/jpeg".equals(file.getContentType()) && !"image/png".equals(file.getContentType())) {
            return "redirect:/result/fail";
        }

        Phone p = phoneService.getByPhone(phone);

        if (p == null) {

            p = new Phone();
            p.setPhone(phone);
            p.setPriority(1);
            phoneService.add(p);

        } else {

            if (p.getPriority() >= maxBadQueries) {

                Ban b = banService.getByPhone(p.getId());
                Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis() - timeToBan);

                if (b == null || (b.getStamp().before(ts) && b.getNumber() < maxBans)) {
                    p.setPriority(1);
                    phoneService.update(p);
                } else {
                    return "redirect:/result/banned";
                }
            }
            Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis() - 1000 * 60 * 60 * 24);
            int queries = markerService.getPhoneRequestsNumberByTime(p.getId(), ts);
            if (queries > maxQueriesPerDay) {
                return "redirect:/result/overquery";
            }

        }

        Geocoder geocoder = new Geocoder();

        try {

            geocoder.geocode(lat, lng);

        } catch (Exception ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/result/unknown";
        }

        if (!"Россия".equals(geocoder.getCountry()) && !"Челябинск".equals(geocoder.getCity())) {
            return "redirect:/result/out";
        }

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());
        String filename = ts.toString();
        filename = filename.replaceAll("\\s", "_");
        try {
            markerService.writeImage(file, filename);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/result/fail";
        }

        Marker m = new Marker();
        m.setLat(lat);
        m.setLng(lng);
        m.setPid(p.getId());
        m.setUrl(filename);
        m.setStamp(ts);
        m.setEnabled(true);
        m.setAddress(geocoder.getAddress());

        markerService.add(m);

        return "redirect:/result/success";
    }

    @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getImage(@PathVariable Long id, HttpServletResponse response) {
        try {
            return markerService.getImageById(id);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @RequestMapping(value = "/result/{result}", method = RequestMethod.GET)
    public String indexGetById(@PathVariable String result, ModelMap model) {

        model.addAttribute("result", result);
        return "result";

    }
    
}
