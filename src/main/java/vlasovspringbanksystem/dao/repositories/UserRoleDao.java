package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.UserRole;

public interface UserRoleDao extends JpaRepository<UserRole, Integer> {
}
