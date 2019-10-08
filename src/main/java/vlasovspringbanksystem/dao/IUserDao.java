package vlasovspringbanksystem.dao;

import vlasovspringbanksystem.entity.User;

import java.util.List;

public interface IUserDao {
    public List<User> getAllUsers();

    public void addNewUser(User newUser);

    User getUserByLogin(String login);
}
