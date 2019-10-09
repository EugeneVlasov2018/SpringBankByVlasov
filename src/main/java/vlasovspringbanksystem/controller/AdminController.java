package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vlasovspringbanksystem.service.AdminService;
import vlasovspringbanksystem.utils.exceptions.UserIsExistException;
import vlasovspringbanksystem.utils.exceptions.ZeroException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class AdminController {
    private AdminService service;
    private static final String ADMINPAGE = "adminpage";
    private static final String NEWUSEPAGE = "newuser";
    private static final String TO_ADMINPAGE = "redirect:/adminpage";

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
        BigDecimal depositPal = new BigDecimal(deposit.replace(',', '.'));
        service.createNewUser(session, fName, lName, login, password, depositPal);
        model.addAttribute("successOperation", true);
        return NEWUSEPAGE;
    }

    @PostMapping("/adminpage/confirmrequest")
    public String confirmUserRequest(@RequestParam("requestId") String id) {
        service.createCreditAccount(id);
        return TO_ADMINPAGE;
    }

    @PostMapping("/adminpage/deleterequest")
    public String refuseUserRequest(@RequestParam("requestId") String id) {
        service.deleteRequest(id);
        return TO_ADMINPAGE;
    }

    @ExceptionHandler(UserIsExistException.class)
    public ModelAndView userIsExistError(UserIsExistException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userisexist", true);
        modelAndView.setViewName(NEWUSEPAGE);
        return modelAndView;
    }

    @ExceptionHandler(ZeroException.class)
    public ModelAndView palIsZero(ZeroException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("zeropal", true);
        modelAndView.setViewName(NEWUSEPAGE);
        return modelAndView;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView palIsLetters(NumberFormatException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("zeropal", true);
        modelAndView.setViewName(NEWUSEPAGE);
        return modelAndView;
    }
}
