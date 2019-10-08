package vlasovspringbanksystem.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vlasovspringbanksystem.entity.User;

public interface UsersRepo extends JpaRepository<User, Integer> {
    User findByUsersLogin(String login);
}
