package org.hae.tasklogue.controllers.tasks;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hae.tasklogue.dto.TaskDTO;
import org.hae.tasklogue.dto.response.AddTaskResponse;
import org.hae.tasklogue.service.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "tasks")
@Slf4j
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PostMapping(value = "/addTask")
    public ResponseEntity<AddTaskResponse> addTask(@Valid @RequestBody TaskDTO taskDTO, Authentication currentUser) throws MessagingException {
        ResponseEntity<AddTaskResponse> addTaskResponseResponseEntity = taskService.addTask(taskDTO, currentUser);
        return addTaskResponseResponseEntity;
    }
}
