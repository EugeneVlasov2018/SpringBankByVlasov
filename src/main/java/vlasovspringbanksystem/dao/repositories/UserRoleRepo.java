package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
    UserRole findByUserRoleValue(String userRoleValue);
}
