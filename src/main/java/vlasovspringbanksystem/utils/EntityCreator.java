package vlasovspringbanksystem.utils;

import org.springframework.stereotype.Component;
import vlasovspringbanksystem.entity.*;
import vlasovspringbanksystem.utils.generators.AccountNumberGenerator;
import vlasovspringbanksystem.utils.generators.PasswordGenerator;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Component
public class EntityCreator {
    private PasswordGenerator generator;
    private LocaleGetter localeGetter;
    private static final String BUNDLE_NAME = "paymentHistory/PaymentHistory";
    private static final BigDecimal CREDIT_RATE = new BigDecimal("10");
    private static final BigDecimal DEPOSIT_RATE = new BigDecimal("5");

    public EntityCreator(PasswordGenerator generator, LocaleGetter localeGetter) {
        this.generator = generator;
        this.localeGetter = localeGetter;
    }

    public User getNewUser(String firstName, String lastName, String login, UserRole userRole, String password) {
        Map<String, String> passwordAndSalt = generator.createPasswordAndSalt(password);

        return User.newBuilder()
                .setUserId(null)
                .setUserFirstname(firstName)
                .setUserLastname(lastName)
                .setUserLoginEmail(login)
                .setUserPassword(passwordAndSalt.get("password"))
                .setUserRole(userRole)
                .setUserHaveCreditAcc(false)
                .setCreditRequestStatus(false)
                .setUserSalt(passwordAndSalt.get("salt"))
                .build();
    }

    public Accounts getNewAccount(User user, Long accountNumber, AccountType type,
                                  BigDecimal initialPal) {
        Accounts result = Accounts.newBuilder()
                .setId(null)
                .setAccountOwner(user)
                .setAccountNumber(accountNumber)
                .setAccountType(type)
                .setCurrentBalance(new BigDecimal("0"))
                .build();

        if (type.getAccountTypeValue().equals("deposit")) {
            result.setCreditLimit(new BigDecimal("0"));
            result.setDeposit(initialPal);
            result.setInterestRate(DEPOSIT_RATE);
            result.setAccountValidity(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));
        } else if (type.getAccountTypeValue().equals("credit")) {
            result.setCreditLimit(initialPal);
            result.setDeposit(new BigDecimal("0"));
            result.setInterestRate(CREDIT_RATE);
            result.setAccountValidity(Timestamp.valueOf(LocalDateTime.now().plusMonths(6)));
        }

        return result;
    }

    public PaymentHistory getNewAction(HttpSession session, TypeOfOperation typeOfOperation,
                                       Accounts account, BigDecimal transactionAmount) {
        Locale currentLocale = localeGetter.getCurrentLocale(session);
        return PaymentHistory.newBuilder()
                .setId(null)
                .setDateOfTransaction(Timestamp.valueOf(LocalDateTime.now()))
                .setAccount(account)
                .setCurrentBalance(account.getCurrentBalance())
                .setTransactionAmount(transactionAmount)
                .setNotification(createNotification(currentLocale, typeOfOperation))
                .build();
    }

    private String createNotification(Locale currentLocale, TypeOfOperation typeOfOperation) {
        String message = "";
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, currentLocale);

        switch (typeOfOperation) {
            case CREATE_CREDIT_ACC:
                message = bundle.getString("create.credit.account");
                break;
            case CREATE_DEPOSIT_ACC:
                message = bundle.getString("create.deposit.account");
                break;
            case REFILL_ACC:
                message = bundle.getString("refill.account");
                break;
            case CREDIT_INTEREST:
                message = bundle.getString("credit.interest");
                break;
            case DEPOSIT_INTEREST:
                message = bundle.getString("deposit.interest");
                break;
            case INCOMING_PAYMENT:
                message = bundle.getString("incoming.payment");
                break;
            case FOREIGN_BANK_PAYMENT:
                message = bundle.getString("foreign.bank.payment");
                break;
            case INTRABANK_PAYMENT:
                message = bundle.getString("intrabank.payment");
                break;
        }
        return message;
    }
}
