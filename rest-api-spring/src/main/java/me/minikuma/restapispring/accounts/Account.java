package me.minikuma.restapispring.accounts;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter @Getter @Builder
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of ="id")
public class Account {
    @Id @GeneratedValue
    private Integer id;

    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;
}
