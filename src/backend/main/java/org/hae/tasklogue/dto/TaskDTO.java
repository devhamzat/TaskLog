package org.hae.tasklogue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hae.tasklogue.utils.enums.TaskStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String tittle;
    private String taskDetails;
    private TaskStatus status;
}
