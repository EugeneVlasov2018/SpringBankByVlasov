package vlasovspringbanksystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vlasovspringbanksystem.service.UserService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller//todo errors bigdecimal
public class RefillAccountController {
    private UserService service;
    private final static String TO_CURRENT_ACC_HISTORY = "redirect:/userpage/accountoperation";

    public RefillAccountController(UserService service) {
        this.service = service;
    }

    @PostMapping("/userpage/refillacc")
    public String refillAccount(HttpSession session, @RequestParam("summ") String summ) {
        BigDecimal summForRefill = new BigDecimal(summ);
        Long accountNumber = Long.valueOf((String) session.getAttribute("accountNumber"));
        service.refillCurrentAcc(session, accountNumber, summForRefill);
        return TO_CURRENT_ACC_HISTORY;
    }
}
