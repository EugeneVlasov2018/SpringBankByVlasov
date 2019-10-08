package vlasovspringbanksystem.dao;

import vlasovspringbanksystem.entity.AccountType;

import java.util.List;

public interface IAccountTypeDao {
    AccountType getAccTypeByValue(String value);
}
