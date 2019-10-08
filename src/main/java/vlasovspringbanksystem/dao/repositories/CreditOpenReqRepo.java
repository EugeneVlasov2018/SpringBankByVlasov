package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.CreditOpeningRequest;

public interface CreditOpenReqRepo extends JpaRepository<CreditOpeningRequest, Integer> {
}
