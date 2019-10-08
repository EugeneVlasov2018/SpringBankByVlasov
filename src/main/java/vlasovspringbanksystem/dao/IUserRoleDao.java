package vlasovspringbanksystem.dao;

import vlasovspringbanksystem.entity.UserRole;

public interface IUserRoleDao {
    UserRole getRoleByValue(String roleValue);
}
