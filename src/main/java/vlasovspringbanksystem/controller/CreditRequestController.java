package vlasovspringbanksystem.controller;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.sql.Timestamp;

@Controller
public class CreditRequestController {

    private UserService service;
    private final static String TO_USERPAGE = "redirect:/userpage";
    private final static String CREDIT_REQUEST_PAGE = "creditrequestform";

    public CreditRequestController(UserService service) {
        this.service = service;
    }


    @GetMapping("userpage/createcreditacc")
    public String getCreditRequestPage(HttpSession session, Model model) {
        LocalDate dateAfter6monts = LocalDate.now().plusMonths(6);

        model.addAttribute("summaryBalance", service.getTotalDepositAfterHalfYear(
                (User) session.getAttribute("user"), dateAfter6monts));
        model.addAttribute("afterHalfYearData", dateAfter6monts);
        return CREDIT_REQUEST_PAGE;
    }

    @PostMapping("userpage/createcreditacc")
    public String sendCreditRequestToAdmin(@RequestParam("totalUserBalance") String totalbalance,
                                           @RequestParam("DataForCreditAcc") String dataForAcc,
                                           @RequestParam("creditLimit") String creditLimit,
                                           HttpSession session,
                                           Model model) {

        User currentUser = (User) session.getAttribute("user");
        BigDecimal creditOfLimit = new BigDecimal(creditLimit.replace(',', '.'));
        BigDecimal balance = new BigDecimal(totalbalance.replace(',', '.'));
        LocalDate date = new LocalDate(dataForAcc);
        Timestamp creditAccValidity = new Timestamp(date.toDateTimeAtStartOfDay().getMillis());

        service.createCreditOpeningRequest(currentUser, creditOfLimit, balance, creditAccValidity);
        currentUser.setCreditRequestStatus(true);
        session.setAttribute("user", currentUser);
        return TO_USERPAGE;
    }

    @ExceptionHandler(ZeroException.class)
    public ModelAndView palIsZero(ZeroException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("zeropal", true);
        modelAndView.setViewName(CREDIT_REQUEST_PAGE);
        return modelAndView;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView palIsLetters(NumberFormatException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("zeropal", true);
        modelAndView.setViewName(CREDIT_REQUEST_PAGE);
        return modelAndView;
    }

}
