package org.hae.tasklogue.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.utils.enums.TaskStatus;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    //    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
    @Column(name = "task_id")
    @Id
    private String taskId;
    private String taskTittle;
    @Column(name = "task_details", columnDefinition = "Text")
    private String taskDetails;
    private TaskStatus status;
    private LocalDate created_At;
    @ManyToOne
    @JoinColumn(name = "userName", nullable = false, updatable = false)
    @CreatedBy
    private ApplicationUser created_By;
    @ManyToMany
    @JoinTable(
            name = "task_collaborators",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "userName")
    )
    private Set<ApplicationUser> collaborators = new HashSet<>();

}
