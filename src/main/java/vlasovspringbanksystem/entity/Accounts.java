package vlasovspringbanksystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "accounts")
@Data
public class Accounts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "current_balance", columnDefinition = "Decimal(10,2)")
    private BigDecimal currentBalance;

    @Column(name = "interest_rate", columnDefinition = "Decimal(10,2)")
    private BigDecimal interestRate;

    @Column(name = "credit_limit", columnDefinition = "Decimal(10,2)")
    private BigDecimal creditLimit;

    @Column(name = "account_validity")
    private Timestamp accountValidity;

    @Column(name = "deposit", columnDefinition = "Decimal(10,2)")
    private BigDecimal deposit;

    @Column(name = "account_number", unique = true, nullable = false)
    private Long accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_types", referencedColumnName = "account_type_value")
    private AccountType accountTypes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_owner", referencedColumnName = "users_login")
    private User accountOwner;

    public static Builder newBuilder() {
        return new Accounts().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(Integer id) {
            Accounts.this.id = id;
            return this;
        }

        public Builder setCurrentBalance(BigDecimal currentBalance) {
            Accounts.this.currentBalance = currentBalance;
            return this;
        }

        public Builder setInterestRate(BigDecimal interestRate) {
            Accounts.this.interestRate = interestRate;
            return this;
        }

        public Builder setCreditLimit(BigDecimal creditLimit) {
            Accounts.this.creditLimit = creditLimit;
            return this;
        }

        public Builder setAccountValidity(Timestamp accountValidity) {
            Accounts.this.accountValidity = accountValidity;
            return this;
        }

        public Builder setDeposit(BigDecimal deposit) {
            Accounts.this.deposit = deposit;
            return this;
        }

        public Builder setAccountNumber(Long accountNumber) {
            Accounts.this.accountNumber = accountNumber;
            return this;
        }

        public Builder setAccountType(AccountType accountTypes) {
            Accounts.this.accountTypes = accountTypes;
            return this;
        }

        public Builder setAccountOwner(User accountOwner) {
            Accounts.this.accountOwner = accountOwner;
            return this;
        }

        public Accounts build() {
            return Accounts.this;
        }
    }
}
