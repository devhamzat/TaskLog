package org.hae.tasklogue.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.utils.enums.TaskStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

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
    @Column(name = "task_id")
    @Id
    private String taskId;
    private String taskTittle;
    @Column(name = "task_details", columnDefinition = "Text")
    private String taskDetails;
    private TaskStatus status;
    @CreatedDate
    private LocalDate created_At;
    @ManyToOne
    @JoinColumn(name = "userName", nullable = false, updatable = false)
    @CreatedBy
    private ApplicationUser created_By;
    @ManyToMany
    private Set<ApplicationUser> collaborators = new HashSet<>();


}
