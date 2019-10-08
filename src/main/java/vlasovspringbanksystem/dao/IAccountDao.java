package vlasovspringbanksystem.dao;

import vlasovspringbanksystem.entity.Accounts;
import vlasovspringbanksystem.entity.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface IAccountDao {
    void saveCurrentAccount(Accounts account);

    List<Accounts> getAllAccounts();

    List<Accounts> getAllAccountsForCurrentUserByType(String usersLogin, String typeOfAccount);

    List<Accounts> getAllAccsOlderThanCurrentDate(String userLogin, Timestamp dateAfter6);

    Accounts getCurrentAccount(Long accountNumber);

    void setCurrentAcc(BigDecimal currentBalance, Long accountNumber);
}
