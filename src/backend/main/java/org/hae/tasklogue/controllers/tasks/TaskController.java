package org.hae.tasklogue.controllers.tasks;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hae.tasklogue.dto.requestdto.AddTaskDTO;
import org.hae.tasklogue.dto.requestdto.GetTaskById;
import org.hae.tasklogue.dto.response.AddTaskResponse;
import org.hae.tasklogue.dto.response.GetTaskResponse;
import org.hae.tasklogue.service.tasks.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AddTaskResponse> addTask(@Valid @RequestBody AddTaskDTO addTaskDTO, Authentication currentUser) throws MessagingException {
        return taskService.addTask(addTaskDTO, currentUser);
    }

    @GetMapping(value = "/allTask")
    public Page<GetTaskResponse> getAllTask(Authentication currentUser, @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {
        log.info("getAllTask");
        return taskService.getAllTasks(currentUser, page, size);
    }

    @PostMapping(value = "/getTask")
    public ResponseEntity<GetTaskResponse> getTaskByID(@RequestBody GetTaskById task_id, Authentication currentUser) {
        log.info("getting task {}", task_id);
        return taskService.getTaskByID(task_id, currentUser);
    }
}
