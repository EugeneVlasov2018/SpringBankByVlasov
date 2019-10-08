package vlasovspringbanksystem.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "credit_opening_requests")
@Data
public class CreditOpeningRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_total_balance", columnDefinition = "Decimal(10,2)")
    private BigDecimal userTotalBalance;

    @Column(name = "expected_credit_limit", columnDefinition = "Decimal(10,2)")
    private BigDecimal expectedCreditLimit;

    @Column(name = "date_of_end_credit")
    private Timestamp dateOfEndCredit;

    @ManyToOne
    @JoinColumn(name = "users_login", referencedColumnName = "users_login")
    private User userLogin;

    public static Builder getBuilder() {
        return new CreditOpeningRequest().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder setId(Integer id) {
            CreditOpeningRequest.this.id = id;
            return this;
        }

        public Builder setUserTotalBalance(BigDecimal userTotalBalance) {
            CreditOpeningRequest.this.userTotalBalance = userTotalBalance;
            return this;
        }

        public Builder setExpectedCreditLimit(BigDecimal expectedCreditLimit) {
            CreditOpeningRequest.this.expectedCreditLimit = expectedCreditLimit;
            return this;
        }

        public Builder setDateOfEndCredit(Timestamp dateOfEndCredit) {
            CreditOpeningRequest.this.dateOfEndCredit = dateOfEndCredit;
            return this;
        }

        public Builder setUserLogin(User userLogin) {
            CreditOpeningRequest.this.userLogin = userLogin;
            return this;
        }

        public CreditOpeningRequest build() {
            return CreditOpeningRequest.this;
        }
    }
}
