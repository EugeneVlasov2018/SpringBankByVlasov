package vlasovspringbanksystem.dao;

import org.springframework.stereotype.Repository;
import vlasovspringbanksystem.dao.repositories.UsersRepo;
import vlasovspringbanksystem.entity.User;

import java.util.List;

@Repository
public class UserDaoImpl implements IUserDao {
    private UsersRepo repository;

    public UserDaoImpl(UsersRepo repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public void addNewUser(User newUser) {
        repository.save(newUser);
    }

    @Override
    public User getUserByLogin(String login) {
        return repository.findByUsersLogin(login);
    }

    @Override
    public void setAllStatusesOfCurrentUser(User requestOwner) {
        repository.setUserStatusesById(requestOwner.getId(),
                requestOwner.getCreditRequestStatus(),
                requestOwner.getHaveCreditAcc());
    }

    @Override
    public void setRequestStatusesOfCurrentUser(User currentUser) {
        repository.setCreditRequestStatusOfCurrentUser(currentUser.getId(), currentUser.getCreditRequestStatus());
    }
}
