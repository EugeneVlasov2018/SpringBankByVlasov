package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogOutController {
    private final static String INDEXPAGE = "redirect:/";

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return INDEXPAGE;
    }
}
