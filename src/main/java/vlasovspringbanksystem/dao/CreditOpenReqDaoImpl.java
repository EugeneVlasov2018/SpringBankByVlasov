package vlasovspringbanksystem.dao;

import org.springframework.stereotype.Repository;
import vlasovspringbanksystem.dao.repositories.CreditOpenReqRepo;
import vlasovspringbanksystem.entity.CreditOpeningRequest;

import java.util.List;

@Repository
public class CreditOpenReqDaoImpl implements ICreditOpeningRequestDao {
    private CreditOpenReqRepo repository;

    public CreditOpenReqDaoImpl(CreditOpenReqRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<CreditOpeningRequest> getAllRequests() {
        return repository.findAll();
    }

    @Override
    public void saveCurrentRequest(CreditOpeningRequest currentRequest) {
        repository.save(currentRequest);
    }
}
