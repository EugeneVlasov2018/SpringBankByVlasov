package vlasovspringbanksystem.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.service.AuthentificationService;

@Controller//todo wrong login exception
@SessionAttributes(value = "user")
public class LogInController {
    private AuthentificationService service;
    private final static String INDEXPAGE = "index";
    private final static String REDIRECT_ADMINPAGE = "redirect:adminpage";
    private final static String REDIRECT_USERPAGE = "redirect:userpage";

    public LogInController(AuthentificationService service) {
        this.service = service;
    }

    @GetMapping(value = "/")
    public String getIndex(Model model) {
        return INDEXPAGE;
    }

    @PostMapping(value = "/")
    public String addUser(@RequestParam("login") String login,
                          @RequestParam("password") String password,
                          Model model) {

        String targetpage = "";
        User currentUser = service.identifyUser(login, password);

        if (currentUser != null) {
            currentUser.setUsersPassword(null);
            currentUser.setSalt(null);

            targetpage = getTargetPage(currentUser);
            model.addAttribute("user", currentUser);
            return targetpage;
        } else {
            model.addAttribute("wrongParameter", true);
            return INDEXPAGE;
        }
    }

    private String getTargetPage(User currentUser) {
        if (currentUser.getRole().getUserRoleValue().equalsIgnoreCase("admin"))
            return REDIRECT_ADMINPAGE;
        else if (currentUser.getRole().getUserRoleValue().equalsIgnoreCase("user"))
            return REDIRECT_USERPAGE;
        else throw new RuntimeException("What are you?!");
    }
}
