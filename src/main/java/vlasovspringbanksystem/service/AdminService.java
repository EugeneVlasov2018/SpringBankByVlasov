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
import vlasovspringbanksystem.utils.exceptions.UserIsExistException;
import vlasovspringbanksystem.utils.exceptions.ZeroException;
import vlasovspringbanksystem.utils.generators.AccountNumberGenerator;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

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
                              String password, BigDecimal deposit) {

        if (deposit.signum() == 0 || deposit.signum() < 0)
            throw new ZeroException();

        Long accountNumber = numberGenerator.getAccountNumber(accountDao.getAllAccounts());

        if (userDao.getUserByLogin(login) == null) {
            User currentUser = creator.getNewUser(fName, lName, login, roleDao.getRoleByValue("user"), password);
            Accounts newAccount = creator.getNewAccount(currentUser, accountNumber,
                    accTypeDao.getAccTypeByValue("deposit"), deposit);
            PaymentHistory firstAction = creator.getNewAction(session, TypeOfOperation.CREATE_DEPOSIT_ACC,
                    newAccount, deposit);

            userDao.addNewUser(currentUser);
            accountDao.saveCurrentAccount(newAccount);
            payDao.saveCurrentAction(firstAction);
        } else {
            throw new UserIsExistException();
        }
    }

    public void createCreditAccount(String id, HttpSession session) {
        CreditOpeningRequest request = requestDao.getRequestById(Integer.valueOf(id)).get();

        User requestOwner = request.getUserLogin();
        requestOwner.setHaveCreditAcc(true);
        requestOwner.setCreditRequestStatus(false);

        Accounts creditAccount = creator.getNewAccount(
                requestOwner,
                numberGenerator.getAccountNumber(accountDao.getAllAccounts()),
                accTypeDao.getAccTypeByValue("credit"),
                request.getExpectedCreditLimit().negate());

        PaymentHistory firsAction = creator.getNewAction(session, TypeOfOperation.CREATE_CREDIT_ACC, creditAccount,
                new BigDecimal("0"));

        requestDao.deleteRequestById(request.getId());
        userDao.setAllStatusesOfCurrentUser(requestOwner);
        accountDao.saveCurrentAccount(creditAccount);
        payDao.saveCurrentAction(firsAction);
    }

    public void deleteRequest(String id) {
        CreditOpeningRequest request = requestDao.getRequestById(Integer.valueOf(id)).get();
        User currentUser = request.getUserLogin();
        currentUser.setCreditRequestStatus(false);

        requestDao.deleteRequestById(request.getId());
        userDao.setRequestStatusesOfCurrentUser(currentUser);
    }
}
