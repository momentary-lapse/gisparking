/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import javax.servlet.http.HttpServletRequest;
import models.MarkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class IndexController {

    @Autowired
    MarkerService markerService;

    @RequestMapping(value = "/")
    public String go() {
        return "redirect:start";
    }

    @RequestMapping(value = "/start/{id}", method = RequestMethod.GET)
    public String indexGetById(@PathVariable Long id, HttpServletRequest request, ModelMap model) {

        String path = "images/mlpfim-twilight-sparkle-wallpaper_1024x768.jpg";
        model.addAttribute("marker", markerService.getById(id));
        model.addAttribute("path", path);
        return "index";

    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String indexGet(ModelMap model) {

        //ml.fillArray();
        model.addAttribute("markers", markerService.getList());
        return "index";

    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String markerGetById(@PathVariable Long id, ModelMap model) {

        model.addAttribute("marker", markerService.getById(id));
        return "marker";

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String markerDeleteById(@PathVariable Long id, ModelMap model) {

        markerService.deleteById(id);
        return "redirect:/start";

    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listGet(ModelMap model) {

        model.addAttribute("list", markerService.getList());
        return "list";

    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String getForm(ModelMap model) {
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String sendData(@RequestParam("image") MultipartFile file, HttpServletRequest request, ModelMap model) {

        String path = "images/" + file.getOriginalFilename();

        File imageFile = new File(path);
        try {
            imageFile.createNewFile();
        } catch (Exception e) {
            model.addAttribute("trace", e.toString());
            return "redirect:/form";
        }
        try {
            file.transferTo(imageFile);
        } catch (Exception e) {
            model.addAttribute("trace", e.toString());
            return "redirect:/form";
        }
        return "redirect:/start";
    }
    
    /*@RequestMapping(value = "/images/{name}", method = RequestMethod.GET)
    public void getImage(@PathVariable String name, HttpServletResponse response) throws IOException {
       
        RandomAccessFile f = null;
        byte[] image = null;
        try {
            f = new RandomAccessFile("images/" + name, "r");
            image = new byte[(int)f.length()];
            f.read(image);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.reset();
        response.setBufferSize(image.length);
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(image, 0, image.length);
        
        out.close();

* 
    }*/
    
}
