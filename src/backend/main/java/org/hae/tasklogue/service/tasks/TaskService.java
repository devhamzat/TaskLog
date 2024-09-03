package org.hae.tasklogue.service.tasks;

import jakarta.mail.MessagingException;
import org.hae.tasklogue.dto.TaskDTO;
import org.hae.tasklogue.dto.response.AddTaskResponse;
import org.hae.tasklogue.dto.response.TaskResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    ResponseEntity<AddTaskResponse> addTask(TaskDTO taskDTO, Authentication connectedUser) throws MessagingException;

    ResponseEntity<TaskResponse> getAllTasks(String username);

    ResponseEntity<TaskResponse> getTask(String taskId);
}
