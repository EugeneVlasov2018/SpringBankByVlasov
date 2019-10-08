package vlasovspringbanksystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlasovspringbanksystem.dao.*;
import vlasovspringbanksystem.entity.Accounts;
import vlasovspringbanksystem.entity.CreditOpeningRequest;
import vlasovspringbanksystem.entity.PaymentHistory;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.utils.EntityCreator;
import vlasovspringbanksystem.utils.TypeOfOperation;
import vlasovspringbanksystem.utils.generators.AccountNumberGenerator;
import vlasovspringbanksystem.utils.generators.PasswordGenerator;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Transactional
public class AdminService {
    private ICreditOpeningRequestDao requestDao;
    private IUserDao userDao;
    private IUserRoleDao roleDao;
    private IAccountDao accountDao;
    private IAccountTypeDao accTypeDao;
    private IPaymentHistoryDao payDao;

    private AccountNumberGenerator numberGenerator;
    private EntityCreator creator;

    public AdminService(ICreditOpeningRequestDao requestDao, IUserDao userDao,
                        IUserRoleDao roleDao, IAccountDao accountDao,
                        IAccountTypeDao accTypeDao, IPaymentHistoryDao payDao,
                        AccountNumberGenerator numberGenerator, EntityCreator creator) {
        this.requestDao = requestDao;
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.accountDao = accountDao;
        this.accTypeDao = accTypeDao;
        this.payDao = payDao;
        this.numberGenerator = numberGenerator;
        this.creator = creator;
    }

    @Transactional(readOnly = true)
    public List<CreditOpeningRequest> getAllCreditRequests() {
        return requestDao.getAllRequests();
    }

    public void createNewUser(HttpSession session, String fName,
                              String lName, String login,
                              String password, String deposit) {
        BigDecimal currentPal = new BigDecimal(deposit.replace(',', '.'));
        Long accountNumber = numberGenerator.getAccountNumber(accountDao.getAllAccounts());

        User currentUser = creator.getNewUser(fName, lName, login, roleDao.getRoleByValue("user"), password);
        Accounts newAccount = creator.getNewAccount(currentUser, accountNumber,
                accTypeDao.getAccTypeByValue("deposit"), currentPal);
        PaymentHistory firstAction = creator.getNewAction(session, TypeOfOperation.CREATE_DEPOSIT_ACC,
                newAccount, currentPal);

        userDao.addNewUser(currentUser);
        accountDao.saveCurrentAccount(newAccount);
        payDao.saveCurrentAction(firstAction);
    }
}
