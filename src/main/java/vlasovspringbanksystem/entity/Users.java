package vlasovspringbanksystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_first_name")
    private String userFirstname;

    @Column(name = "user_last_name")
    private String userLastname;

    @Column(name = "user_login_email", nullable = false, unique = true)
    private String userLoginEmail;

    @Column(name = "user_password")
    private String userPassword;

    @ManyToOne
    @JoinColumn(name = "user_role", referencedColumnName = "user_role_value")
    private UserRole userRole;

    @Column(name = "user_credit_acc")
    private Boolean userCreditAcc;

    @Column(name = "credit_request_status")
    private Boolean creditRequestStatus;

    @Column(name = "salt")
    private String salt;

    @OneToMany(mappedBy = "accountOwner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "usersLogin", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreditOpeningRequest> requests = new HashSet<>();
}
