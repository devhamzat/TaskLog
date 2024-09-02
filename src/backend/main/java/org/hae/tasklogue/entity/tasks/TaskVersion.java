package org.hae.tasklogue.entity.tasks;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.utils.enums.TaskStatus;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Table(name = "task_versions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskVersion {

    @Id
    @GeneratedValue(strategy = UUID)
    private java.util.UUID id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @Column(nullable = false)
    private Integer versionNumber;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private ApplicationUser createdBy;
}

