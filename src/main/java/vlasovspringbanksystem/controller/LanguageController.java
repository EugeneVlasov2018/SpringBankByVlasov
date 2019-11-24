package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class LanguageController {

    @PostMapping("/changelang")
    public String setLanguage(HttpServletRequest request, @RequestParam("language") String language) {
        request.getSession().setAttribute("currentLang", language);
        return "redirect:/";
    }
}
