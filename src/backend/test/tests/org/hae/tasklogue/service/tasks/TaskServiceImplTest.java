package org.hae.tasklogue.service.tasks;

import org.hae.tasklogue.dto.requestdto.AddTaskDTO;
import org.hae.tasklogue.dto.requestdto.GetTaskById;
import org.hae.tasklogue.dto.response.AddTaskResponse;
import org.hae.tasklogue.dto.response.GetTaskResponse;
import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.entity.tasks.Task;
import org.hae.tasklogue.exceptions.errors.EmptyRequiredFields;
import org.hae.tasklogue.exceptions.errors.TaskNotExisting;
import org.hae.tasklogue.repository.ApplicationUserRepository;
import org.hae.tasklogue.repository.taskrepository.TaskRepository;
import org.hae.tasklogue.service.email.CollaboratorEmailService;
import org.hae.tasklogue.utils.TaskIdGenerator;
import org.hae.tasklogue.utils.enums.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskIdGenerator taskIdGenerator;

    @Mock
    private CollaboratorEmailService collaboratorEmailService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addTask_Success() throws Exception {

        AddTaskDTO addTaskDTO = new AddTaskDTO();
        addTaskDTO.setTitle("Test Task");
        addTaskDTO.setTaskDetails("Test Details");
        Set<ApplicationUser> collaborators = new HashSet<>();
        ApplicationUser collaborator = new ApplicationUser();
        collaborator.setUserName("collaborator");
        collaborator.setEmail("collaborator@example.com");
        collaborators.add(collaborator);
        addTaskDTO.setCollaboratorUsernames(collaborators);

        ApplicationUser user = new ApplicationUser();
        user.setUserName("testUser");

        when(authentication.getName()).thenReturn("testUser");
        when(applicationUserRepository.findApplicationUserByUserName("testUser")).thenReturn(Optional.of(user));
        when(applicationUserRepository.findApplicationUserByUserName("collaborator")).thenReturn(Optional.of(collaborator));
        when(taskIdGenerator.generateTaskId()).thenReturn("TASK-001");

        ResponseEntity<AddTaskResponse> response = taskService.addTask(addTaskDTO, authentication);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("TASK-001", response.getBody().getTaskId());
        assertEquals("Task added successfully", response.getBody().getMessage());

        verify(taskRepository, times(1)).save(any(Task.class));
        verify(collaboratorEmailService, times(1)).sendEmail(anyString(), anyString(), any(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void getAllTasks_Success() {

        ApplicationUser user = new ApplicationUser();
        user.setUserName("testUser");

        Task task1 = new Task();
        task1.setTaskId("TASK-001");
        task1.setTaskTittle("Task 1");
        task1.setCreatedBy(user);

        Task task2 = new Task();
        task2.setTaskId("TASK-002");
        task2.setTaskTittle("Task 2");
        task2.setCreatedBy(user);

        List<Task> tasks = Arrays.asList(task1, task2);
        Page<Task> taskPage = new PageImpl<>(tasks);

        when(authentication.getName()).thenReturn("testUser");
        when(applicationUserRepository.findApplicationUserByUserName("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findAllByCreatedBy(eq(user), any(PageRequest.class))).thenReturn(taskPage);

        Page<GetTaskResponse> result = taskService.getAllTasks(authentication, 0, 10);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("TASK-001", result.getContent().get(0).getTaskId());
        assertEquals("TASK-002", result.getContent().get(1).getTaskId());
    }

    @Test
    void getTaskById_Success() {

        GetTaskById getTaskById = new GetTaskById();
        getTaskById.setTaskId("TASK-001");

        ApplicationUser user = new ApplicationUser();
        user.setUserName("testUser");

        Task task = new Task();
        task.setTaskId("TASK-001");
        task.setTaskTittle("Test Task");
        task.setTaskDetails("Test Details");
        task.setStatus(TaskStatus.pending);
        task.setCreatedBy(user);

        when(authentication.getName()).thenReturn("testUser");
        when(applicationUserRepository.findApplicationUserByUserName("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId("TASK-001")).thenReturn(Optional.of(task));

        ResponseEntity<GetTaskResponse> response = taskService.getTaskByID(getTaskById, authentication);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("TASK-001", response.getBody().getTaskId());
        assertEquals("Test Task", response.getBody().getTaskTitle());
        assertEquals("Test Details", response.getBody().getTaskDetails());
        assertEquals("pending", response.getBody().getTaskStatus());
    }

    @Test
    void should_throw_taskNotExisting_when_task_not_found() {

        GetTaskById getTaskById = new GetTaskById();
        getTaskById.setTaskId("TASK-999");

        ApplicationUser user = new ApplicationUser();
        user.setUserName("testUser");

        when(authentication.getName()).thenReturn("testUser");
        when(applicationUserRepository.findApplicationUserByUserName("testUser")).thenReturn(Optional.of(user));
        when(taskRepository.findByTaskId("TASK-999")).thenReturn(Optional.empty());

        assertThrows(TaskNotExisting.class, () -> taskService.getTaskByID(getTaskById, authentication));
    }

    @Test
    void should_throw_emptyRequired_when_required_field_is_empty() {

        GetTaskById getTaskById = new GetTaskById();
        getTaskById.setTaskId("");

        ApplicationUser user = new ApplicationUser();
        user.setUserName("testUser");

        when(authentication.getName()).thenReturn("testUser");
        when(applicationUserRepository.findApplicationUserByUserName("testUser")).thenReturn(Optional.of(user));


        assertThrows(EmptyRequiredFields.class, () -> taskService.getTaskByID(getTaskById, authentication));
    }
}