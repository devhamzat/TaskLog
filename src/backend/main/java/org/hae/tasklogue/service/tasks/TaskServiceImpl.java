package org.hae.tasklogue.service.tasks;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.hae.tasklogue.dto.TaskDTO;
import org.hae.tasklogue.dto.response.AddTaskResponse;
import org.hae.tasklogue.dto.response.TaskResponse;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.entity.tasks.Task;
import org.hae.tasklogue.exceptions.errors.ForbiddenRequest;
import org.hae.tasklogue.repository.ApplicationUserRepository;
import org.hae.tasklogue.repository.taskrepository.TaskRepository;
import org.hae.tasklogue.service.email.CollaboratorEmailService;
import org.hae.tasklogue.utils.TaskIdGenerator;
import org.hae.tasklogue.utils.enums.EmailTemplateName;
import org.hae.tasklogue.utils.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
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
    public ResponseEntity<AddTaskResponse> addTask(TaskDTO addTask, Authentication connectedUser) throws MessagingException {


        if (connectedUser == null) {
            throw new ForbiddenRequest("Forbidden request: User is not authenticated.");
        }

        String username = connectedUser.getName();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Task task = new Task();
        task.setTaskId(taskIdGenerator.generateTaskId());
        task.setTaskTittle(addTask.getTitle());
        task.setTaskDetails(addTask.getTaskDetails());
        task.setStatus(TaskStatus.pending);
        task.setCreated_By(user);
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
    public ResponseEntity<TaskResponse> getAllTasks(String username) {
        return null;
    }

    @Override
    public ResponseEntity<TaskResponse> getTask(String taskId) {
        return null;
    }


}
