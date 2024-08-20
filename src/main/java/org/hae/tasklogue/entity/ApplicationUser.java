package org.hae.tasklogue.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ApplicationUser {

//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
    @Column(name = "created_at",nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Id
    @Column(name = "userName",nullable = false)
    private String userName;
    @Column(name = "display_name")
    private String displayName;
    @Column(name = "email",nullable = false)
    @Email
    private String email;
    @Column(name = "password",nullable = false)
    private String secretPassword;
    private String bio;
    private String photoUrl;
    @OneToMany
    private Set<Task> userTasks =new HashSet<>();
    private boolean isEnabled;
}
