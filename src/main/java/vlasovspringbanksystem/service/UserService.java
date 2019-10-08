package vlasovspringbanksystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlasovspringbanksystem.dao.IAccountDao;
import vlasovspringbanksystem.dao.IAccountTypeDao;
import vlasovspringbanksystem.dao.ICreditOpeningRequestDao;
import vlasovspringbanksystem.dao.IPaymentHistoryDao;
import vlasovspringbanksystem.entity.Accounts;
import vlasovspringbanksystem.entity.CreditOpeningRequest;
import vlasovspringbanksystem.entity.PaymentHistory;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.utils.EntityCreator;
import vlasovspringbanksystem.utils.TypeOfOperation;
import vlasovspringbanksystem.utils.generators.AccountNumberGenerator;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
public class UserService {
    private IAccountDao accountDao;
    private IAccountTypeDao accTypeDao;
    private IPaymentHistoryDao paymentDao;
    private ICreditOpeningRequestDao requestDao;
    private AccountNumberGenerator generator;
    private EntityCreator creator;

    public UserService(IAccountDao accountDao, IAccountTypeDao accTypeDao,
                       IPaymentHistoryDao paymentDao, ICreditOpeningRequestDao requestDao,
                       AccountNumberGenerator generator, EntityCreator creator) {
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

    public void createNewDepositAccount(HttpSession session, String deposit, User currentUser) {
        BigDecimal depositPal = new BigDecimal(deposit.replace(',', '.'));
        Long accountNumber = generator.getAccountNumber(accountDao.getAllAccounts());

        Accounts newAccount = creator.getNewAccount(currentUser, accountNumber,
                accTypeDao.getAccTypeByValue("deposit"), depositPal);

        PaymentHistory firstAction = creator.getNewAction(session, TypeOfOperation.CREATE_DEPOSIT_ACC,
                newAccount, depositPal);

        accountDao.saveCurrentAccount(newAccount);
        paymentDao.saveCurrentAction(firstAction);

    }

    @Transactional(readOnly = true)
    public BigDecimal getTotalDepositAfterHalfYear(User user, LocalDateTime dateAfter6monts) {
        Timestamp dateAfter6 = Timestamp.valueOf(dateAfter6monts);
        List<Accounts> totalBalance = accountDao.getAllAccsOlderThanCurrentDate(user.getUsersLogin(), dateAfter6);

        BigDecimal result = new BigDecimal("0");

        for (Accounts currentAcc : totalBalance) {
            result = result.add(currentAcc.getDeposit());
        }

        return result;
    }

    public void createCreditOpeningRequest(User currentUser, BigDecimal creditLimit,
                                           BigDecimal balance, Timestamp creditAccValidity) {
        CreditOpeningRequest currentRequest = CreditOpeningRequest.getBuilder()
                .setId(null)
                .setUserLogin(currentUser)
                .setDateOfEndCredit(creditAccValidity)
                .setExpectedCreditLimit(creditLimit)
                .setUserTotalBalance(balance)
                .build();

        currentUser.setCreditRequestStatus(true);

        requestDao.saveCurrentRequest(currentRequest);
    }

    @Transactional(readOnly = true)
    public Page<PaymentHistory> getTotalHistoryForCurrentAcc(Long accNumber, Pageable pageable) {
        return paymentDao.getAllHistoriesForCurrentAcc(accNumber, pageable);
    }

    public void refillCurrentAcc(HttpSession session, Long accountNumber, BigDecimal summForRefill) {
        Accounts currrentAccount = accountDao.getCurrentAccount(accountNumber);
        BigDecimal accountsBalance = currrentAccount.getCurrentBalance().add(summForRefill);

        PaymentHistory currentAction = creator.getNewAction(session,
                TypeOfOperation.REFILL_ACC, currrentAccount, summForRefill);

        accountDao.setCurrentAcc(accountsBalance, currrentAccount.getAccountNumber());
        paymentDao.saveCurrentAction(currentAction);
    }

    public void doTransaction(Long accountNumber, BigDecimal transactionAmount) {

    }

    public void doTransaction(Long accountNumber, Long recipientAccount, BigDecimal transactionAmount) {

    }
}
