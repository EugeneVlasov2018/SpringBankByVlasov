package vlasovspringbanksystem.utils.generators;

import org.springframework.stereotype.Component;
import vlasovspringbanksystem.entity.Accounts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class AccountNumberGenerator {
    public Long getAccountNumber(List<Accounts> accounts) {
        List<Long> accountNumbers = new ArrayList<>();
        for (Accounts currentAcc : accounts) {
            accountNumbers.add(currentAcc.getAccountNumber());
        }

        int endOfRange = 999999999;
        int startOfRange = 99999999;

        Random random = new Random();
        int range = endOfRange - startOfRange + 1;
        long accountNumber = random.nextInt(range) + 1;

        if (!accountNumbers.isEmpty()) {
            while (accountNumbers.contains(accountNumber)) {
                accountNumber = random.nextInt(range) + 1;
            }
        }
        return accountNumber;
    }
}