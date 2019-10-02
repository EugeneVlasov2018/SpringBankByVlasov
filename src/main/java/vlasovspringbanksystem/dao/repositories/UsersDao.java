package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.Users;

public interface UsersDao extends JpaRepository<Users, Integer> {
}
