package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.AccountType;

public interface AcoountsTypeDao extends JpaRepository<AccountType, Integer> {
}
