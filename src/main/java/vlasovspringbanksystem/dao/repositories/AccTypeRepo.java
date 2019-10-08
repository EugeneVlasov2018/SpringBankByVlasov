package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.AccountType;

public interface AccTypeRepo extends JpaRepository<AccountType, Integer> {
    AccountType findByAccountTypeValue(String accountTypeValue);
}
