package vlasovspringbanksystem.service;

import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlasovspringbanksystem.dao.*;
import vlasovspringbanksystem.entity.Accounts;
import vlasovspringbanksystem.entity.CreditOpeningRequest;
import vlasovspringbanksystem.entity.PaymentHistory;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.utils.EntityCreator;
import vlasovspringbanksystem.utils.TypeOfOperation;
import vlasovspringbanksystem.utils.exceptions.UnexistUserException;
import vlasovspringbanksystem.utils.exceptions.ZeroException;
import vlasovspringbanksystem.utils.generators.AccountNumberGenerator;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class UserService {
    private IUserDao userDao;
    private IAccountDao accountDao;
    private IAccountTypeDao accTypeDao;
    private IPaymentHistoryDao paymentDao;
    private ICreditOpeningRequestDao requestDao;
    private AccountNumberGenerator generator;
    private EntityCreator creator;

    public UserService(IUserDao userDao, IAccountDao accountDao, IAccountTypeDao accTypeDao,
                       IPaymentHistoryDao paymentDao, ICreditOpeningRequestDao requestDao,
                       AccountNumberGenerator generator, EntityCreator creator) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.accTypeDao = accTypeDao;
        this.paymentDao = paymentDao;
        this.requestDao = requestDao;
        this.generator = generator;
        this.creator = creator;
    }

    @Transactional(readOnly = true)
    public List<Accounts> getAllAccountsForCurrentUserByType(String usersLogin, String typeOfAccount) {
        return accountDao.getAllAccountsForCurrentUserByType(usersLogin, typeOfAccount);
    }

    public void createNewDepositAccount(HttpSession session, BigDecimal deposit, User currentUser) {
        if (deposit.signum() == 0 || deposit.signum() < 0)
            throw new ZeroException();

        Long accountNumber = generator.getAccountNumber(accountDao.getAllAccounts());

        Accounts newAccount = creator.getNewAccount(currentUser, accountNumber,
                accTypeDao.getAccTypeByValue("deposit"), deposit);

        PaymentHistory firstAction = creator.getNewAction(session, TypeOfOperation.CREATE_DEPOSIT_ACC,
                newAccount, deposit);

        accountDao.saveCurrentAccount(newAccount);
        paymentDao.saveCurrentAction(firstAction);

    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalDepositAfterHalfYear(User user, LocalDate dateAfter6monts) {
        Timestamp dateAfter6 = new Timestamp(dateAfter6monts.toDateTimeAtStartOfDay().getMillis());

        List<Accounts> totalBalance = accountDao.getAllAccsOlderThanCurrentDate(user.getUsersLogin(), dateAfter6);

        BigDecimal result = new BigDecimal("0");

        for (Accounts currentAcc : totalBalance) {
            result = result.add(currentAcc.getDeposit());
        }
        return result;
    }

    public void createCreditOpeningRequest(User currentUser, BigDecimal creditLimit,
                                           BigDecimal balance, Timestamp creditAccValidity) {
        if (creditLimit.signum() == 0 || creditLimit.signum() < 0)
            throw new ZeroException();

        CreditOpeningRequest currentRequest = CreditOpeningRequest.getBuilder()
                .setId(null)
                .setUserLogin(currentUser)
                .setDateOfEndCredit(creditAccValidity)
                .setExpectedCreditLimit(creditLimit)
                .setUserTotalBalance(balance)
                .build();

        currentUser.setCreditRequestStatus(true);

        requestDao.saveCurrentRequest(currentRequest);
        userDao.setRequestStatusesOfCurrentUser(currentUser);
    }

    @Transactional(readOnly = true)
    public Page<PaymentHistory> getTotalHistoryForCurrentAcc(Long accNumber, Pageable pageable) {
        return paymentDao.getAllHistoriesForCurrentAcc(accNumber, pageable);
    }

    public void refillCurrentAcc(HttpSession session, Long accountNumber, BigDecimal summForRefill) {
        if (summForRefill.signum() == 0 || summForRefill.signum() < 0)
            throw new ZeroException();

        Accounts currrentAccount = accountDao.getCurrentAccount(accountNumber);
        BigDecimal accountsBalance = currrentAccount.getCurrentBalance().add(summForRefill);
        currrentAccount.setCurrentBalance(accountsBalance);

        PaymentHistory currentAction = creator.getNewAction(session,
                TypeOfOperation.REFILL_ACC, currrentAccount, summForRefill);

        accountDao.setCurrentAcc(accountsBalance, currrentAccount.getAccountNumber());
        paymentDao.saveCurrentAction(currentAction);
    }

    public void doTransaction(HttpSession session, BigDecimal transactionAmount) {
        if (transactionAmount.signum() == 0 || transactionAmount.signum() < 0)
            throw new ZeroException();

        Long accountNumber = Long.valueOf((String) session.getAttribute("accountNumber"));
        Accounts account = accountDao.getCurrentAccount(accountNumber);

        BigDecimal balance = account.getCurrentBalance().subtract(transactionAmount);
        account.setCurrentBalance(balance);

        PaymentHistory currentAction = creator.getNewAction(session, TypeOfOperation.FOREIGN_BANK_PAYMENT,
                account, transactionAmount);

        accountDao.setCurrentAcc(balance, accountNumber);
        paymentDao.saveCurrentAction(currentAction);
    }


    public void doTransaction(HttpSession session, Long recipientAccount, BigDecimal transactionAmount) {
        if (transactionAmount.signum() == 0 || transactionAmount.signum() < 0)
            throw new ZeroException();

        Long accountNumber = Long.valueOf((String) session.getAttribute("accountNumber"));
        Accounts donorAcc = accountDao.getCurrentAccount(accountNumber);
        Accounts recipientAcc = accountDao.getCurrentAccount(recipientAccount);

        if (recipientAcc == null) {
            throw new UnexistUserException();
        }

        BigDecimal donorBalance = donorAcc.getCurrentBalance().subtract(transactionAmount);
        BigDecimal recipientBalance = recipientAcc.getCurrentBalance().add(transactionAmount);

        donorAcc.setCurrentBalance(donorBalance);
        recipientAcc.setCurrentBalance(recipientBalance);

        PaymentHistory donorAction = creator.getNewAction(session, TypeOfOperation.INTRABANK_PAYMENT,
                donorAcc, transactionAmount);
        PaymentHistory recipientAction = creator.getNewAction(session, TypeOfOperation.INCOMING_PAYMENT,
                recipientAcc, transactionAmount);

        accountDao.setCurrentAcc(donorBalance, accountNumber);
        accountDao.setCurrentAcc(recipientBalance, recipientAcc.getAccountNumber());

        paymentDao.saveCurrentAction(donorAction);
        paymentDao.saveCurrentAction(recipientAction);
    }

    public boolean accountHaveEnoughMoney(HttpSession session, BigDecimal transactionAmount) {

        Long accountNumber = Long.valueOf((String) session.getAttribute("accountNumber"));
        Accounts account = accountDao.getCurrentAccount(accountNumber);

        BigDecimal currentBal = account.getCurrentBalance();
        BigDecimal creditLimit = account.getCreditLimit().negate();

        return (currentBal.add(creditLimit).subtract(transactionAmount).signum() > 0);
    }
}
