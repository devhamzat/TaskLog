package org.hae.tasklogue.dto.response;

import lombok.Data;
import org.hae.tasklogue.utils.enums.TaskPriority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class GetTaskResponse {
    private String taskId;
    private String taskTitle;
    private String taskDetails;
    private String taskStatus;
    private LocalDate createdAt;
    private LocalDate beginDate;
    private LocalDate dueDate;
    private TaskPriority priority;
    private LocalTime beginTime;
    private LocalTime dueTime;
    private String createdBy;
    private List<String> collaborators;
}
