package vlasovspringbanksystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

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

    public static Builder newBuilder() {
        return new User().new Builder();
    }

    public class Builder {

        public Builder() {
        }

        public Builder setUserId(Integer id) {
            User.this.id = id;
            return this;
        }

        public Builder setUserFirstname(String firstName) {
            User.this.firstName = firstName;
            return this;
        }

        public Builder setUserLastname(String lastName) {
            User.this.lastName = lastName;
            return this;
        }

        public Builder setUserLoginEmail(String usersLogin) {
            User.this.usersLogin = usersLogin;
            return this;
        }

        public Builder setUserPassword(String usersPassword) {
            User.this.usersPassword = usersPassword;
            return this;
        }

        public Builder setUserRole(UserRole role) {
            User.this.role = role;
            return this;
        }

        public Builder setUserSalt(String salt) {
            User.this.salt = salt;
            return this;
        }

        public Builder setUserHaveCreditAcc(Boolean haveCreditAcc) {
            User.this.haveCreditAcc = haveCreditAcc;
            return this;
        }

        public Builder setCreditRequestStatus(Boolean creditRequestStatus) {
            User.this.creditRequestStatus = creditRequestStatus;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
