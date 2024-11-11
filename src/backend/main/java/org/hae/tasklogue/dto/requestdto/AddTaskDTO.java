package org.hae.tasklogue.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.utils.enums.TaskPriority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskDTO {
    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalDate beginDate;
    private LocalTime dueTime;
    private LocalTime beginTime;
    private TaskPriority priority;
    private Set<ApplicationUser> collaboratorUsernames = new HashSet<>();
}
