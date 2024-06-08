package com.example.serverticketingsystem.user.models;

import com.example.serverticketingsystem.system.security.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDateTime;
import java.util.Collection;


@Table(name = "users")
@Entity(name = "User")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Data
public class User implements UserDetails{
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "id", updatable = false)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Email(message = "Invalid email")
    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false,length = 20)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "active", nullable = false)
    private boolean active;

    public User(long id, String username, String mail, UserRole role, LocalDateTime createdAt, boolean active) {
        this.id = id;
        this.username = username;
        this.mail = mail;
        this.role = role;
        this.createdAt = createdAt;
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public String toString() {
        String text= "User: " + this.username+"\n" +
                "Mail: " + this.mail+"\n" +
                " Role: " + this.role+"\n"  +
                " Created At: " + this.createdAt+"\n"  +
                " Active: " + this.active+"\n" ;
        return text;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.grantedAuthority();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername(){
        return this.username;
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
