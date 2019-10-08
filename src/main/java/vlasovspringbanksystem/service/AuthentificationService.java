package vlasovspringbanksystem.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vlasovspringbanksystem.dao.IUserDao;
import vlasovspringbanksystem.entity.User;
import vlasovspringbanksystem.utils.generators.PasswordGenerator;

@Service
@Transactional
public class AuthentificationService {
    private IUserDao userDao;
    private PasswordGenerator generator;

    public AuthentificationService(IUserDao userDao, PasswordGenerator generator) {
        this.userDao = userDao;
        this.generator = generator;
    }


    @Transactional(readOnly = true)
    public User identifyUser(String login, String password) {
        User user = userDao.getUserByLogin(login);
        if (user != null) {
            if (generator.checkPassword(user, password))
                return user;
        }
        return null;
    }
}
