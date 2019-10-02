package vlasovspringbanksystem.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "credit_opening_requests")
@Data
@NoArgsConstructor
public class CreditOpeningRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_total_balance", columnDefinition = "Decimal(10,2)")
    private BigDecimal userTotalBalance;

    @Column(name = "expected_credit_limit", columnDefinition = "Decimal(10,2)")
    private BigDecimal expectedCreditLimit;

    @Column(name = "date_of_end_credit")
    private Timestamp dateOfEndCredit;

    @ManyToOne
    @JoinColumn(name = "users_login", referencedColumnName = "users_login")
    private Users usersLogin;
}
