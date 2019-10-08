package vlasovspringbanksystem.dao;

import vlasovspringbanksystem.entity.CreditOpeningRequest;

import java.util.List;

public interface ICreditOpeningRequestDao {
    List<CreditOpeningRequest> getAllRequests();

    void saveCurrentRequest(CreditOpeningRequest currentRequest);
}
