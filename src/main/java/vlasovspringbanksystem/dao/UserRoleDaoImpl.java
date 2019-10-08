package vlasovspringbanksystem.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vlasovspringbanksystem.dao.repositories.UserRoleRepo;
import vlasovspringbanksystem.entity.UserRole;

@Repository
public class UserRoleDaoImpl implements IUserRoleDao {

    private UserRoleRepo repository;

    public UserRoleDaoImpl(UserRoleRepo repository) {
        this.repository = repository;
    }

    @Override
    public UserRole getRoleByValue(String roleValue) {
        return repository.findByUserRoleValue(roleValue);
    }
}
