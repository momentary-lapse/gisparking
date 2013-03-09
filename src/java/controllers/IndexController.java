/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.sun.xml.wss.impl.policy.MLSPolicy;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import models.MarkerLibrary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

    MarkerLibrary ml = new MarkerLibrary();
    
    
    
    
    @RequestMapping(value = "index.htm", method = RequestMethod.GET)
    public String indexGet(ModelMap model) {
       
        ml.fillArray();
        model.addAttribute("marks", ml.getMarkers());
        return "index";

    }
    
    @RequestMapping(value = "index.htm", method = RequestMethod.POST)
    public String indexPost(HttpServletRequest request, ModelMap model) {

        ml.deleteById(Long.parseLong(request.getParameter("id")));
        model.addAttribute("marks", ml.getMarkers());
        return "index";

    }
    
    
}
