package vlasovspringbanksystem.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "payment_history")
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "transaction_amount", scale = 6, precision = 2)
    private BigDecimal transactionAmount;

    @Column(name = "current_balance", scale = 6, precision = 2)
    private BigDecimal currentBalance;

    @Column(name = "date_of_transaction")
    private Timestamp dateOfTransaction;

    @Column
    private String notification;

    @ManyToOne
    @JoinColumn(name = "account_number", referencedColumnName = "account_number")
    private Account accNumber;


}
