package vlasovspringbanksystem.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "payment_histories")
@Data
public class PaymentHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer id;

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

    public static Builder newBuilder() {
        return new PaymentHistory().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(Integer id) {
            PaymentHistory.this.id = id;
            return this;
        }

        public Builder setTransactionAmount(BigDecimal transactionAmount) {
            PaymentHistory.this.transactionAmount = transactionAmount;
            return this;
        }

        public Builder setCurrentBalance(BigDecimal currentBalance) {
            PaymentHistory.this.currentBalance = currentBalance;
            return this;
        }

        public Builder setDateOfTransaction(Timestamp dateOfTransaction) {
            PaymentHistory.this.dateOfTransaction = dateOfTransaction;
            return this;
        }

        public Builder setNotification(String notification) {
            PaymentHistory.this.notification = notification;
            return this;
        }

        public Builder setAccount(Accounts account) {
            PaymentHistory.this.account = account;
            return this;
        }

        public PaymentHistory build() {
            return PaymentHistory.this;
        }
    }
}
