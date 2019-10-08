package vlasovspringbanksystem.dao;

import org.springframework.stereotype.Repository;
import vlasovspringbanksystem.dao.repositories.AccountsRepo;
import vlasovspringbanksystem.entity.Accounts;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class AccountDaoImpl implements IAccountDao {
    private AccountsRepo repository;

    public AccountDaoImpl(AccountsRepo repository) {
        this.repository = repository;
    }

    @Override
    public void saveCurrentAccount(Accounts account) {
        repository.save(account);
    }

    @Override
    public List<Accounts> getAllAccounts() {
        return repository.findAll();
    }

    @Override
    public List<Accounts> getAllAccountsForCurrentUserByType(String usersLogin, String typeOfAccount) {
        return repository.findAllByAccountOwner_UsersLoginAndAccountTypes_AccountTypeValue(usersLogin, typeOfAccount);
    }

    @Override
    public List<Accounts> getAllAccsOlderThanCurrentDate(String userLogin, Timestamp dateAfter6monts) {
        return repository.findAllByAccountOwner_UsersLoginAndAccountValidityAfter(userLogin, dateAfter6monts);
    }

    @Override
    public Accounts getCurrentAccount(Long accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }

    @Override
    public void setCurrentAcc(BigDecimal currentBalance, Long accountNumber) {
        repository.setBalanceByAccNumber(currentBalance, accountNumber);
    }

}
