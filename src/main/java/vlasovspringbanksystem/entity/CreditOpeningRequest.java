package vlasovspringbanksystem.entity;


import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "credit_opening_request")
public class CreditOpeningRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_total_balance", scale = 6, precision = 2)
    private BigDecimal userTotalBalance;

    @Column(name = "expected_credit_limit", scale = 6, precision = 2)
    private BigDecimal expectedCreditLimit;

    @Column(name = "date_of_end_credit")
    private Timestamp dateOfEndCredit;

    @ManyToOne
    @JoinColumn(name = "user_login", referencedColumnName = "user_login_email")
    private Users usersLogin;
}
