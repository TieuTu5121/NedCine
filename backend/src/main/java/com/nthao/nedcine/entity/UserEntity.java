package com.nthao.nedcine.entity;

import com.nthao.nedcine.contants.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table (name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String sex;
    private String birthday;
    private String address;
    private String phone;
    @ElementCollection (targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated (EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Set<GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) role)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Đặt giá trị là true
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Đặt giá trị là true
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Đặt giá trị là true
    }

    @Override
    public boolean isEnabled() {
        return true; // Đặt giá trị là true
    }
}
