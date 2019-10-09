package vlasovspringbanksystem.dao.repositories;

import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vlasovspringbanksystem.entity.User;

public interface UsersRepo extends JpaRepository<User, Integer> {
    User findByUsersLogin(String login);

    @Modifying
    @Query("UPDATE User u SET u.creditRequestStatus=?2, u.haveCreditAcc=?3 where u.id=?1")
    void setUserStatusesById(Integer id, Boolean creditRequestStatus, Boolean haveCreditAcc);

    @Modifying
    @Query("UPDATE User u SET u.creditRequestStatus=?2 where u.id=?1")
    void setCreditRequestStatusOfCurrentUser(Integer id, Boolean creditRequestStatus);
}
