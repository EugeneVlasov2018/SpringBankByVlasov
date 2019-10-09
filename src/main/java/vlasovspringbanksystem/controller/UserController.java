package vlasovspringbanksystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vlasovspringbanksystem.entity.PaymentHistory;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    private UserService service;
    private final static String USERPAGE = "userpage";
    private final static String CURRENT_ACC_HISTORY_PAGE = "userhistorypage";

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

    private void preparePage(Long accountNumber, String pageNumber, Model model) {
        int page = 1;
        int recordsPerPage = 5;
        if (pageNumber != null)
            page = Integer.parseInt(pageNumber);

        Page<PaymentHistory> histories = service.getTotalHistoryForCurrentAcc(
                accountNumber,
                PageRequest.of((page - 1), recordsPerPage,
                        new Sort(Sort.Direction.DESC, "dateOfTransaction")
                )
        );

        model.addAttribute("history", histories.getContent());
        model.addAttribute("numberOfPage", histories.getTotalPages());
        model.addAttribute("currentPage", page);
    }
}
