package org.hae.tasklogue.service.tasks;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hae.tasklogue.dto.requestdto.AddTaskDTO;
import org.hae.tasklogue.dto.requestdto.GetTaskById;
import org.hae.tasklogue.dto.response.AddTaskResponse;
import org.hae.tasklogue.dto.response.GetTaskResponse;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.entity.tasks.Task;
import org.hae.tasklogue.exceptions.errors.EmptyRequiredFields;
import org.hae.tasklogue.exceptions.errors.ForbiddenRequest;
import org.hae.tasklogue.exceptions.errors.TaskNotExisting;
import org.hae.tasklogue.repository.ApplicationUserRepository;
import org.hae.tasklogue.repository.taskrepository.TaskRepository;
import org.hae.tasklogue.service.email.CollaboratorEmailService;
import org.hae.tasklogue.utils.TaskIdGenerator;
import org.hae.tasklogue.utils.enums.EmailTemplateName;
import org.hae.tasklogue.utils.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    @Value("${application.mailing.frontend.collaboration-acceptance}")
    private String acceptanceUrl;
    private final CollaboratorEmailService collaboratorEmailService;
    private final TaskIdGenerator taskIdGenerator;
    private final TaskRepository taskRepository;
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public TaskServiceImpl(ApplicationUserRepository applicationUserRepository, TaskRepository taskRepository, TaskIdGenerator taskIdGenerator, CollaboratorEmailService collaboratorEmailService) {
        this.applicationUserRepository = applicationUserRepository;
        this.taskRepository = taskRepository;
        this.taskIdGenerator = taskIdGenerator;
        this.collaboratorEmailService = collaboratorEmailService;

    }

    @Override
    @Transactional
    public ResponseEntity<AddTaskResponse> addTask(AddTaskDTO addTask, Authentication connectedUser) throws MessagingException {

        String username = checkConnectedUser(connectedUser);
        ApplicationUser user = applicationUserRepository.findApplicationUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Task task = new Task();
        task.setTaskId(taskIdGenerator.generateTaskId());
        task.setTaskTittle(addTask.getTitle());
        task.setTaskDetails(addTask.getTaskDetails());
        task.setStatus(TaskStatus.pending);
        task.setCreatedBy(user);
        if (addTask.getCollaboratorUsernames() != null && !addTask.getCollaboratorUsernames().isEmpty()) {
            Set<ApplicationUser> collaborators = new HashSet<>(addTask.getCollaboratorUsernames());
            for (ApplicationUser collaboratorUsername : addTask.getCollaboratorUsernames()) {
                ApplicationUser collaborator = applicationUserRepository.findApplicationUserByUserName(collaboratorUsername.getUsername())
                        .orElseThrow(() -> new UsernameNotFoundException("collaborator username not found"));
                collaborators.add(collaborator);
                collaboratorEmailService.sendEmail(
                        collaborator.getEmail(),
                        collaborator.getUsername(),
                        EmailTemplateName.Accept_collaboration,
                        acceptanceUrl + task.getTaskId(),
                        task.getTaskId(),
                        task.getTaskTittle(),
                        user.getUsername(),
                        "Collaboration Invite"
                );
            }
            task.setCollaborators(collaborators);
        }


        taskRepository.save(task);
        AddTaskResponse addTaskResponse = new AddTaskResponse();
        addTaskResponse.setTaskId(task.getTaskId());
        addTaskResponse.setMessage("Task added successfully");
        return ResponseEntity.ok(addTaskResponse);

    }


    @Override
    public Page<GetTaskResponse> getAllTasks(Authentication connectedUser, int page, int size) {
        String username = checkConnectedUser(connectedUser);
        ApplicationUser user = applicationUserRepository.findApplicationUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Pageable pageable = PageRequest.of(page, size);
        Page<Task> taskPage = taskRepository.findAllByCreatedBy(user, pageable);


        log.info("Retrieving tasks for user: {}, page: {}, size: {}", username, page, size);
        return taskPage.map(this::convertToDto);
    }


    @Override
    public ResponseEntity<GetTaskResponse> getTaskByID(GetTaskById taskById, Authentication connectedUser) {
        String username = checkConnectedUser(connectedUser);
        applicationUserRepository.findApplicationUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found:   " + username));

        String retrievedTaskId = taskById.getTaskId();
        if (retrievedTaskId == null || retrievedTaskId.isEmpty()) {
            log.info("task id empty");
            throw new EmptyRequiredFields("Task ID cannot be null or empty.");
        }

        Optional<Task> task = taskRepository.findByTaskId(retrievedTaskId);
        return task.map(foundTask -> {
            log.info("Retrieved task with ID: {}", foundTask.getTaskId());
            GetTaskResponse response = new GetTaskResponse();
            response.setTaskId(foundTask.getTaskId());
            response.setCreatedAt(foundTask.getCreated_At());
            response.setTaskDetails(foundTask.getTaskDetails());
            response.setTaskStatus(String.valueOf(foundTask.getStatus()));
            response.setTaskTitle(foundTask.getTaskTittle());
            List<String> collaboratorNames = foundTask.getCollaborators().stream()
                    .map(ApplicationUser::getUsername)
                    .collect(Collectors.toList());
            response.setCollaborators(collaboratorNames);
            return ResponseEntity.ok(response);
        }).orElseThrow(() -> new TaskNotExisting("Task not found"));
    }


    private GetTaskResponse convertToDto(Task task) {
        GetTaskResponse dto = new GetTaskResponse();
        dto.setTaskId(task.getTaskId());
        dto.setTaskTitle(task.getTaskTittle());
        dto.setTaskDetails(task.getTaskDetails());
        dto.setCreatedAt(task.getCreated_At());
        dto.setTaskStatus(String.valueOf(task.getStatus()));
        dto.setCreatedBy(task.getCreatedBy().getUsername());
        dto.setCollaborators(task.getCollaborators().stream()
                .map(ApplicationUser::getUsername)
                .collect(Collectors.toList()));
        return dto;
    }

    private String checkConnectedUser(Authentication connectedUser) {
        if (connectedUser == null) {
            throw new ForbiddenRequest("Forbidden request: User is not authenticated.");
        }
        return connectedUser.getName();
    }
}
