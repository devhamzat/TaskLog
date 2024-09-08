package org.hae.tasklogue.service.tasks;

import jakarta.mail.MessagingException;
import org.hae.tasklogue.dto.requestdto.AddTaskDTO;
import org.hae.tasklogue.dto.response.AddTaskResponse;
import org.hae.tasklogue.dto.response.GetTaskResponse;
import org.hae.tasklogue.entity.tasks.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    ResponseEntity<AddTaskResponse> addTask(AddTaskDTO addTaskDTO, Authentication connectedUser) throws MessagingException;

    Page<GetTaskResponse> getAllTasks(Authentication connectedUser);

    ResponseEntity<GetTaskResponse> getTask(String taskId);
}
