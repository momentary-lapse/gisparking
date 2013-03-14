/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.sun.xml.wss.impl.policy.MLSPolicy;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import models.MarkerLibrary;
import org.jboss.weld.context.http.HttpRequestContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    MarkerLibrary ml = new MarkerLibrary();

    @RequestMapping(value = "/")
    public String go() {
        return "redirect:start";
    }

    @RequestMapping(value = "/start/{id}", method = RequestMethod.GET)
    public String indexGetById(@PathVariable int id, ModelMap model) {

        model.addAttribute("marker", ml.getById(id));
        return "index";

    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public String indexGet(ModelMap model) {

        //ml.fillArray();
        model.addAttribute("markers", ml.getMarkers());
        return "index";

    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String markerGetById(@PathVariable int id, ModelMap model) {

        model.addAttribute("marker", ml.getById(id));
        return "marker";

    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String markerDeleteById(@PathVariable int id, ModelMap model) {

        ml.deleteById(id);
        return "redirect:/start";

    }
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listGet(ModelMap model) {

        model.addAttribute("list", ml.getMarkers());
        return "list";

    }
    
    
}
