package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class LanguageController {

    @PostMapping("/changelang")
    public String setLanguage(HttpSession session, @RequestParam("language") String language) {
        session.setAttribute("currentLang", language);
        return "redirect:/";
    }
}
