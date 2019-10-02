package vlasovspringbanksystem.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "user_role")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_role_value", nullable = false, unique = true)
    private String userRoleValue;

    @OneToMany(mappedBy = "user_role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Users> users = new HashSet<>();
}
