package org.hae.tasklogue.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTaskDTO {
    private String title;
    private String taskDetails;

    private Set<ApplicationUser> collaboratorUsernames = new HashSet<>();
}
