package vlasovspringbanksystem.dao;

import vlasovspringbanksystem.entity.CreditOpeningRequest;

import java.util.List;
import java.util.Optional;

public interface ICreditOpeningRequestDao {
    List<CreditOpeningRequest> getAllRequests();

    void saveCurrentRequest(CreditOpeningRequest currentRequest);

    Optional<CreditOpeningRequest> getRequestById(Integer id);

    void deleteRequestById(Integer id);
}
