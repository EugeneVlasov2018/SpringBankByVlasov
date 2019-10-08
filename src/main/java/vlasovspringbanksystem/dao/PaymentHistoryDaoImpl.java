package vlasovspringbanksystem.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import vlasovspringbanksystem.dao.repositories.PaymentHistoryRepo;
import vlasovspringbanksystem.entity.PaymentHistory;

import java.util.List;

@Repository
public class PaymentHistoryDaoImpl implements IPaymentHistoryDao {
    private PaymentHistoryRepo repository;

    public PaymentHistoryDaoImpl(PaymentHistoryRepo repository) {
        this.repository = repository;
    }


    @Override
    public void saveCurrentAction(PaymentHistory currentHistory) {
        repository.save(currentHistory);
    }

    @Override
    public List<PaymentHistory> getAllHistories() {
        return repository.findAll();
    }

    @Override
    public Page<PaymentHistory> getAllHistoriesForCurrentAcc(Long accNumber, Pageable pageable) {
        return repository.findAllByAccount_AccountNumber(accNumber, pageable);
    }
}
