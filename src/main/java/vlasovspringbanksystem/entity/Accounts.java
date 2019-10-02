package vlasovspringbanksystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor

public class Accounts {
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

    @Column(name = "deposit")
    private BigDecimal deposit;

    @Column(name = "account_number", unique = true, nullable = false)
    private Long accountNumber;

    @ManyToOne
    @JoinColumn(name = "account_types", referencedColumnName = "account_type_value")
    private AccountType accountTypes;

    @ManyToOne
    @JoinColumn(name = "account_owner", referencedColumnName = "users_login")
    private Users accountOwner;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<PaymentHistory> histories;
}
