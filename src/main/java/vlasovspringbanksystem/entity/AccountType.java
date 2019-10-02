package vlasovspringbanksystem.entity;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account_types")
@Data
@NoArgsConstructor

public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_type_value", unique = true, nullable = false)
    private String accountTypeValue;

    @OneToMany(mappedBy = "accountTypes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Accounts> accounts = new HashSet<>();
}
