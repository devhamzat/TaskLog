package org.hae.tasklogue.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hae.tasklogue.entity.Task;

import java.util.HashSet;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserResponse {
    private String userName;
    private String displayName;
    private String email;
    private String bio;
    private String photoUrl;
    private Set<Task> userTasks = new HashSet<>();

}
