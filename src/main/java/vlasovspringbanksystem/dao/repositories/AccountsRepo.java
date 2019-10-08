package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vlasovspringbanksystem.entity.Accounts;
import vlasovspringbanksystem.entity.User;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public interface AccountsRepo extends JpaRepository<Accounts, Integer> {
    List<Accounts> findAllByAccountOwner_UsersLoginAndAccountTypes_AccountTypeValue(
            String usersLogin, String accountTypeValue
    );

    List<Accounts> findAllByAccountOwner_UsersLoginAndAccountValidityAfter(String usersLogin, Timestamp after6monts);

    Accounts findByAccountNumber(Long accountNumber);

    @Modifying
    @Query("UPDATE Accounts a SET a.currentBalance = ?1 where a.accountNumber = ?2")
    void setBalanceByAccNumber(BigDecimal currentBalance, Long accountNumber);
}
