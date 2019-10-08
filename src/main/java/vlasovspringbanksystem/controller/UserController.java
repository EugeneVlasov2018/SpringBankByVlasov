package vlasovspringbanksystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vlasovspringbanksystem.entity.PaymentHistory;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.service.UserService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
public class UserController {
    private UserService service;
    private final static String USERPAGE = "userpage";
    private final static String NEW_DEPOSIT_ACC_PAGE = "newDepositAcc";
    private final static String TO_USERPAGE = "redirect:/userpage";
    private final static String TO_CURRENT_ACC_HISTORY = "redirect:/userpage/accountoperation";
    private final static String CREDIT_REQUEST_PAGE = "creditrequestform";
    private final static String CURRENT_ACC_HISTORY_PAGE = "userhistorypage";
    private final static String REFILL_ACCOUNT = "addmoneypage";
    private final static String SEND_MONEY_PAGE = "currentAccOperation";

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/userpage")
    public String getUserPage(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        String usersLogin = currentUser.getUsersLogin();
        model.addAttribute("depositAccounts",
                service.getAllAccountsForCurrentUserByType(usersLogin, "deposit"));
        model.addAttribute("creditAccounts",
                service.getAllAccountsForCurrentUserByType(usersLogin, "credit"));
        return USERPAGE;
    }

    @GetMapping("userpage/createaccount")
    public String getNewDepositAccPage() {
        return NEW_DEPOSIT_ACC_PAGE;
    }

    @PostMapping("userpage/createaccount")//todo реализовать валидатор на ненулевой депозит
    public String createDepositAccPage(@RequestParam("deposit") String deposit,
                                       HttpSession session) {
        service.createNewDepositAccount(session, deposit, (User) session.getAttribute("user"));
        return TO_USERPAGE;
    }

    @GetMapping("userpage/createcreditacc")
    public String getCreditRequestPage(HttpSession session, Model model) {
        LocalDateTime dateAfter6monts = LocalDateTime.now().plusMonths(6);

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
        Timestamp creditAccValidity = Timestamp.valueOf(dataForAcc); //todo преобразование в дату не работает
        service.createCreditOpeningRequest(currentUser, creditOfLimit, balance, creditAccValidity);
        currentUser.setCreditRequestStatus(true);
        session.setAttribute("user", currentUser);
        return TO_USERPAGE;
    }

    @GetMapping("/userpage/accountoperation")
    public String getCurrentAccountHistory(HttpSession session,
                                           Model model,
                                           @RequestParam(value = "accountNumber", required = false) String accNumber,
                                           @RequestParam(value = "page", required = false) String pageNumber) {
        String accountNumber = accNumber;
        if (accountNumber != null) {
            session.setAttribute("accountNumber", accountNumber);
        } else {
            accountNumber = (String) session.getAttribute("accountNumber");
        }
        preparePage(Long.valueOf(accountNumber), pageNumber, model);
        return CURRENT_ACC_HISTORY_PAGE;
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

    @PostMapping("/userpage/refillacc")
    public String refillAccount(HttpSession session, @RequestParam("summ") String summ) {
        BigDecimal summForRefill = new BigDecimal(summ);
        Long accountNumber = Long.valueOf((String) session.getAttribute("accountNumber"));
        service.refillCurrentAcc(session, accountNumber, summForRefill);
        return TO_CURRENT_ACC_HISTORY;
    }

    @PostMapping("/userpage/payment")//todo прописать валидатор на номер входящего аккаунта
    public String payment(HttpSession session,
                          @RequestParam("typeOfTransaction") String typeOfTransaction,
                          @RequestParam("account") String recipientAcc,
                          @RequestParam("money") String money) {

        BigDecimal transactionAmount = new BigDecimal(money);
        if (typeOfTransaction.equals("anotherbank")) {
            service.doTransaction(Long.valueOf((String) session.getAttribute("accountNumber")), transactionAmount);
        } else if (typeOfTransaction.equals("currentBank")) {
            service.doTransaction(Long.valueOf((String) session.getAttribute("accountNumber")),
                    Long.valueOf(recipientAcc), transactionAmount);
        }
        return TO_CURRENT_ACC_HISTORY;
    }

    private void preparePage(Long accountNumber, String pageNumber, Model model) {
        int page = 1;
        int recordsPerPage = 5;
        if (pageNumber != null)
            page = Integer.parseInt(pageNumber);

        Page<PaymentHistory> histories = service.getTotalHistoryForCurrentAcc(
                accountNumber,
                PageRequest.of((page - 1), recordsPerPage));

        model.addAttribute("history", histories.getContent());
        model.addAttribute("numberOfPage", histories.getTotalPages());
        model.addAttribute("currentPage", page);
    }
}
