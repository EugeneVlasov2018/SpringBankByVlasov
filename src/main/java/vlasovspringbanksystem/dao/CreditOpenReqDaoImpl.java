package vlasovspringbanksystem.dao;

import org.springframework.stereotype.Repository;
import vlasovspringbanksystem.dao.repositories.CreditOpenReqRepo;
import vlasovspringbanksystem.entity.CreditOpeningRequest;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<CreditOpeningRequest> getRequestById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public void deleteRequestById(Integer id) {
        repository.deleteById(id);
    }
}
