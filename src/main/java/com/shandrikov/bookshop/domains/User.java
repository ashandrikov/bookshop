package com.shandrikov.bookshop.domains;

import com.shandrikov.bookshop.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "login")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String name;
//    private String lastname;
    @Column(unique = true)
    private String login;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

//    @Column(name = "account_non_locked")
//    private boolean accountNonLocked;

//    @Column(name = "failed_attempt")
//    private int failedAttempt;

//    public void removeRole(Role role){
//        if (this.getAuthorities().contains(role)){
////            userGroups.remove(role);
//        }
//    }


    public User(AuthenticationRequest request) {
        this.login = request.getLogin();
        this.password = request.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(role);
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
//        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
