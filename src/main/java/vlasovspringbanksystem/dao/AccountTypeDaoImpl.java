package vlasovspringbanksystem.dao;

import org.springframework.stereotype.Repository;
import vlasovspringbanksystem.dao.repositories.AccTypeRepo;
import vlasovspringbanksystem.entity.AccountType;

@Repository
public class AccountTypeDaoImpl implements IAccountTypeDao {
    private AccTypeRepo repository;

    public AccountTypeDaoImpl(AccTypeRepo repository) {
        this.repository = repository;
    }

    @Override
    public AccountType getAccTypeByValue(String value) {
        return repository.findByAccountTypeValue(value);
    }
}
