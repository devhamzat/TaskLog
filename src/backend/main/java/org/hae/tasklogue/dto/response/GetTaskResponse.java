package org.hae.tasklogue.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetTaskResponse {
    private String taskId;
    private String taskTitle;
    private String taskDetails;
    private String taskStatus;
    private LocalDateTime createdAt;
    private String createdBy;
    private List<String> collaborators;
}
