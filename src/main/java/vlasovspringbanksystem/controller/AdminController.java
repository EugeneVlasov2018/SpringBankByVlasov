package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlasovspringbanksystem.service.AdminService;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
public class AdminController {
    private AdminService service;
    private static final String ADMINPAGE = "adminpage";
    private static final String NEWUSEPAGE = "newuser";

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping("/adminpage")
    public String getAdminpage(Model model) {
        model.addAttribute("allUserRequests", service.getAllCreditRequests());
        return ADMINPAGE;
    }

    @GetMapping("/adminpage/newuserpage")
    public String getNewUserMenu() {
        return NEWUSEPAGE;
    }

    @PostMapping("/adminpage/newuserpage")
    public String createNewUser(@RequestParam("firstname") String fName,
                                @RequestParam("lastname") String lName,
                                @RequestParam("userLogin") String login,
                                @RequestParam("password") String password,
                                @RequestParam("deposit") String deposit,
                                HttpSession session,
                                Model model) {
        service.createNewUser(session, fName, lName, login, password, deposit);
        model.addAttribute("successOperation", true);
        return NEWUSEPAGE;
    }
}
