package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.service.UserService;
import vlasovspringbanksystem.utils.exceptions.ZeroException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class NewAccFromUserController {
    private UserService service;
    private final static String TO_USERPAGE = "redirect:/userpage";
    private final static String NEW_DEPOSIT_ACC_PAGE = "newDepositAcc";

    public NewAccFromUserController(UserService service) {
        this.service = service;
    }

    @GetMapping("userpage/createaccount")
    public String getNewDepositAccPage() {
        return NEW_DEPOSIT_ACC_PAGE;
    }

    @PostMapping("userpage/createaccount")//todo реализовать валидатор на ненулевой депозит
    public String createDepositAccPage(@RequestParam("deposit") String deposit,
                                       HttpSession session) {
        BigDecimal depositPal = new BigDecimal(deposit.replace(',', '.'));
        service.createNewDepositAccount(session, depositPal, (User) session.getAttribute("user"));
        return TO_USERPAGE;
    }


    @ExceptionHandler(ZeroException.class)
    public ModelAndView palIsZero(ZeroException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("zeropal", true);
        modelAndView.setViewName(NEW_DEPOSIT_ACC_PAGE);
        return modelAndView;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView palIsLetters(NumberFormatException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("zeropal", true);
        modelAndView.setViewName(NEW_DEPOSIT_ACC_PAGE);
        return modelAndView;
    }
}
