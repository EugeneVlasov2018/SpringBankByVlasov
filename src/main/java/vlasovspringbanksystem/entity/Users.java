package vlasovspringbanksystem.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "users_login", nullable = false, unique = true)
    private String usersLogin;

    @Column(name = "users_password")
    private String usersPassword;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "users_role_value")
    private UserRole role;

    @Column(name = "have_credit_acc")
    private Boolean haveCreditAcc;

    @Column(name = "credit_request_status")
    private Boolean creditRequestStatus;

    @Column(name = "salt")
    private String salt;

    @OneToMany(mappedBy = "accountOwner", cascade = CascadeType.ALL)
    private Set<Accounts> accounts = new HashSet<>();

    @OneToMany(mappedBy = "usersLogin", cascade = CascadeType.ALL)
    private Set<CreditOpeningRequest> requests = new HashSet<>();
}
