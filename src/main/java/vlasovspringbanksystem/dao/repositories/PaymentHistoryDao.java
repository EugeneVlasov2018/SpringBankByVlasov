package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.PaymentHistory;

public interface PaymentHistoryDao extends JpaRepository<PaymentHistory, Integer> {
}
