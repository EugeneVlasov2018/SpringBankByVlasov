package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.PaymentHistory;

public interface PaymentHistoryRepo extends JpaRepository<PaymentHistory, Integer> {
    Page<PaymentHistory> findAllByAccount_AccountNumber(Long accountNumber, Pageable pageable);
}
