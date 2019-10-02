package vlasovspringbanksystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_owner", referencedColumnName = "user_login_email")
    private Users accountOwner;


    @ManyToOne
    @JoinColumn(name = "account_type", referencedColumnName = "account_type_value")
    private AccountType accountType;

    @Column(name = "current_balance", scale = 6, precision = 2)
    private BigDecimal currentBalance;

    @Column(name = "interest_rate", scale = 6, precision = 2)
    private BigDecimal interestRate;

    @Column(name = "credit_limit")
    private BigDecimal creditLimit;

    @Column(name = "account_validity")
    private Timestamp accountValidity;

    @Column(name = "deposit")
    private BigDecimal deposit;

    @Column(name = "account_number", unique = true, nullable = false)
    private Long accountNumber;

    @OneToMany(mappedBy = "account_number", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaymentHistory> histories = new HashSet<>();
}
