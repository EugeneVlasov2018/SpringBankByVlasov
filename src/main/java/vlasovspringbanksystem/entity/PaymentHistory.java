package vlasovspringbanksystem.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "payment_histories")
@Data
@NoArgsConstructor
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "transaction_amount", columnDefinition = "Decimal(10,2)")
    private BigDecimal transactionAmount;

    @Column(name = "current_balance", columnDefinition = "Decimal(10,2)")
    private BigDecimal currentBalance;

    @Column(name = "date_of_transaction")
    private Timestamp dateOfTransaction;

    @Column(name = "notification")
    private String notification;

    @ManyToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private Accounts account;
}
