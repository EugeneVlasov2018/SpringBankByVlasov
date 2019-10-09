package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class LogOutController {
    private final static String INDEXPAGE = "redirect:/";

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println("before " + session);
        session.invalidate();
        System.out.println("after " + session);
        return INDEXPAGE;
    }
}
