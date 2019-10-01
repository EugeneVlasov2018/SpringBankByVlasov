package vlasovspringbanksystem.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping(value = "/")
    public String getIndex(
            @RequestParam (name = "greetings", required = false, defaultValue = "Hello, World")String name,
                           Model model)
    {
        model.addAttribute("name", name);
        return "index";
    }

}
