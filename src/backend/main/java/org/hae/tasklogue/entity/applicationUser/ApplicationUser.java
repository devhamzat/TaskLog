package org.hae.tasklogue.entity.applicationUser;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hae.tasklogue.entity.Role;
import org.hae.tasklogue.entity.tasks.Task;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ApplicationUser implements UserDetails, Principal {

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false, name = "updated_at")
    private LocalDateTime lastModifiedDate;

    @Id
    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    private String secretPassword;

    @Column(name = "bio")
    private String bio;

    @Column(name = "display_picture")
    private String photoUrl;

    @OneToMany()
    private Set<Task> userTasks = new HashSet<>();

    private boolean isAccountEnabled;

    private boolean isAccountLocked;

    @ManyToMany(fetch = EAGER)
    private List<Role> roles;


    @Override
    public String getName() {
        return userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return secretPassword;
    }

    @Override
    public boolean isEnabled() {
        return isAccountEnabled;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
