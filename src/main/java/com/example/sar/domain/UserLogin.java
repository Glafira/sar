package com.example.sar.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@Table
public class UserLogin implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        private String username;
        private String password;
        private String email;

        @ManyToMany
        private Set<Role> roles;
//    @Transient — указывает, что свойство не нужно записывать. Значения под этой аннотацией не записываются в базу данных (так же не участвуют в сериализации). static и final переменные экземпляра всегда transient.
        @Transient
        private Set<GrantedAuthority> authorities;

    public UserLogin() {
    }

    public UserLogin(Long id, String username, String password, String email, Set<GrantedAuthority> set) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.authorities = set;
        this.password = password;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
