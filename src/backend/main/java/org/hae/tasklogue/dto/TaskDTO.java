package org.hae.tasklogue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.utils.enums.TaskStatus;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String title;
    private String taskDetails;
    private Set<ApplicationUser> collaboratorUsernames = new HashSet<>();
}
