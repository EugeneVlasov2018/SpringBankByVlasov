package vlasovspringbanksystem.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vlasovspringbanksystem.entity.PaymentHistory;

import java.util.List;

public interface IPaymentHistoryDao {
    void saveCurrentAction(PaymentHistory currentHistory);

    List<PaymentHistory> getAllHistories();

    Page<PaymentHistory> getAllHistoriesForCurrentAcc(Long accNumber, Pageable pageable);
}
