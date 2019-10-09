package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import vlasovspringbanksystem.service.UserService;
import vlasovspringbanksystem.utils.exceptions.ZeroException;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
public class PaymentController {//todo errors BigDecimal
    private UserService service;
    private final static String TO_CURRENT_ACC_HISTORY = "redirect:/userpage/accountoperation";
    private final static String REFILL_ACCOUNT = "addmoneypage";
    private final static String SEND_MONEY_PAGE = "currentAccOperation";


    public PaymentController(UserService service) {
        this.service = service;
    }

    @GetMapping("/userpage/workwithaccount")
    public String selectOperationMenu(@RequestParam("typeofwork") String typeOfOperation) {
        String currentMenu = "";
        if (typeOfOperation.equals("addmoney"))
            currentMenu = REFILL_ACCOUNT;
        else if (typeOfOperation.equals("sendmoney"))
            currentMenu = SEND_MONEY_PAGE;
        return currentMenu;
    }

    @PostMapping("/userpage/payment")//todo прописать валидатор на номер входящего аккаунта
    public String payment(HttpSession session,
                          Model model,
                          @RequestParam("typeOfTransaction") String typeOfTransaction,
                          @RequestParam("account") String recipientAcc,
                          @RequestParam("money") String money) {

        BigDecimal transactionAmount = new BigDecimal(money);
        if (service.accountHaveEnoughMoney(session, transactionAmount)) {
            if (typeOfTransaction.equals("anotherbank")) {
                service.doTransaction(session, transactionAmount);
            } else if (typeOfTransaction.equals("currentBank")) {
                service.doTransaction(session, Long.valueOf(recipientAcc), transactionAmount);
            }
            return TO_CURRENT_ACC_HISTORY;
        } else {
            model.addAttribute("noEnoughMoney", true);
            return "currentAccOperation";
        }
    }

    @ExceptionHandler(ZeroException.class)
    public ModelAndView palIsZero(ZeroException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("zeropal", true);
        modelAndView.setViewName(SEND_MONEY_PAGE);
        return modelAndView;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView palIsLetters(NumberFormatException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("zeropal", true);
        modelAndView.setViewName(SEND_MONEY_PAGE);
        return modelAndView;
    }

}
