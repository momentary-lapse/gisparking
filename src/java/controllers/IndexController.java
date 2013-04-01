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
import org.springframework.beans.factory.annotation.Autowired;
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

    private String imagePath = "../images/";
    final int imageWidth = 300;
    final int imageHeigth = 200;
    final double chelLat = 55.17;
    final double chelLng = 61.4;
    final int maxBadQueries = 5;
    final int maxQueriesPerDay = 25;
    final int maxBans = 5;
    final int daysOfBan = 30;
    final int timeToBan = 1000 * 60 * 60 * 24 * daysOfBan;
    //final int timeToBan = 1000 * 60;
    
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
    public String indexGetById(@PathVariable Long id, HttpServletRequest request, ModelMap model) {

        model.addAttribute("marker", markerService.getById(id));
        return "index";

    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String indexGet(ModelMap model) {


        model.addAttribute("markers", markerService.getEnabledList());
        return "index";

    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String markerGetById(@PathVariable Long id, ModelMap model) {

        model.addAttribute("marker", markerService.getById(id));
        return "marker";

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String markerDeleteById(@PathVariable Long id, ModelMap model) {

        Marker m = markerService.getById(id);
        File f = new File(imagePath + m.getUrl());
        f.delete();
        markerService.deleteById(id);

        return "redirect:/result/success";

    }

    @RequestMapping(value = "/choose/{id}", method = RequestMethod.GET)
    public String markerChoose(@PathVariable Long id, ModelMap model) {

        Marker m = markerService.getById(id);
        if (m.getEnabled() == 0) {
            return "redirect:/result/busy";
        }
        m.setEnabled(0);
        markerService.update(m);

        return "redirect:/start/{id}";

    }

    @RequestMapping(value = "/complain/{id}", method = RequestMethod.GET)
    public String complainPhone(@PathVariable Long id, ModelMap model) {

        Marker m = markerService.getById(id);
        Phone p = phoneService.getByPhone(m.getPhone());

        if (p == null) {

            p = new Phone();
            p.setPhone(m.getPhone());
            p.setPriority(2);
            phoneService.add(p);

            return "redirect:/delete/" + id.toString();

        }

        p.setPriority(p.getPriority() + 1);

        phoneService.update(p);
        
        if (p.getPriority() >= maxBadQueries) {
            Ban b = banService.getLastByPhone(p.getPhone());
            if (b == null) {
                b = new Ban();
                b.setPhone(p.getPhone());
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
        m.setEnabled(1);
        markerService.update(m);

        return "redirect:/start";

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listGet(ModelMap model) {

        model.addAttribute("list", markerService.getEnabledList());
        return "list";

    }

    @RequestMapping(value = "/geolist", method = RequestMethod.GET)
    public String geoListGet(@RequestParam("lat") double lat, @RequestParam("lng") double lng, ModelMap model) {

        model.addAttribute("list", markerService.getSortedList(lat, lng));
        return "list";

    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getForm(ModelMap model) {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String sendData(@RequestParam("north") Double north, @RequestParam("east") Double east, @RequestParam("phone") String phone, @RequestParam("image") MultipartFile file, HttpServletRequest request, ModelMap model) {

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

                Ban b = banService.getLastByPhone(phone);
                Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis() - timeToBan);

                if (b == null || (b.getStamp().before(ts) && b.getNumber() < maxBans)) {
                    p.setPriority(1);
                    phoneService.update(p);
                } else {
                    return "redirect:/result/banned";
                }
            }
            Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis() - 1000 * 60 * 60 * 24);
            int queries = markerService.getPhoneRequestsNumberByTime(p.getPhone(), ts);
            if (queries > maxQueriesPerDay) {
                return "redirect:/result/overquery";
            }

        }

        Geocoder geocoder = new Geocoder();

        try {

            geocoder.geocode(north, east);

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
        String path = imagePath + filename;

        File imageFile = new File(path);

        try {
            imageFile.getParentFile().createNewFile();
            BufferedImage image = ImageIO.read(file.getInputStream());
            image = ImageTransformer.scaleByHeigth(image, imageHeigth);
            imageFile.createNewFile();
            ImageIO.write(image, "jpg", imageFile);

        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/result/fail";
        }

        Marker m = new Marker();
        m.setNorth(north);
        m.setEast(east);
        m.setPhone(phone);
        m.setUrl(filename);
        m.setStamp(ts);
        m.setEnabled(1);
        m.setAddress(geocoder.getAddress());

        markerService.add(m);

        return "redirect:/result/success";
    }

    @RequestMapping(value = "/images/{id}", method = RequestMethod.GET, produces = "image/jpg")
    public @ResponseBody
    byte[] getImage(@PathVariable Long id, HttpServletResponse response) throws IOException {

        Marker m = markerService.getById(id);
        BufferedImage image = ImageIO.read(new File(imagePath + m.getUrl()));
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", bao);
        return bao.toByteArray();

    }

    @RequestMapping(value = "/result/{result}", method = RequestMethod.GET)
    public String indexGetById(@PathVariable String result, ModelMap model) {

        model.addAttribute("result", result);
        return "result";

    }
}
