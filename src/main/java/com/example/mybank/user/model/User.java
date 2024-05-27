package com.example.mybank.user.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;
    @Column
    private String fullName;
    @Column(unique = true)
    private String login;
    @Column
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "phones", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "phone", unique = true)
    private Set<String> phones;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "emails", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "email", unique = true)
    private Set<String> emails;
    @Column
    private LocalDateTime birthday;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return login;
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
}