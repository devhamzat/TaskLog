package org.hae.tasklogue.service.tasks;

import jakarta.mail.MessagingException;
import org.hae.tasklogue.dto.requestdto.AddTaskDTO;
import org.hae.tasklogue.dto.requestdto.GetTaskById;
import org.hae.tasklogue.dto.response.AddTaskResponse;
import org.hae.tasklogue.dto.response.GetTaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    ResponseEntity<AddTaskResponse> addTask(AddTaskDTO addTaskDTO, Authentication connectedUser) throws MessagingException;

    Page<GetTaskResponse> getAllTasks(Authentication connectedUser,int page,int size);

    ResponseEntity<GetTaskResponse> getTaskByID(GetTaskById taskById, Authentication authentication);
}
