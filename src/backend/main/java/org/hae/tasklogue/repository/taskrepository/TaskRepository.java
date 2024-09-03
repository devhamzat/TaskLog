package org.hae.tasklogue.repository.taskrepository;

import org.hae.tasklogue.entity.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {

    Optional<Task> findByTaskId(String taskId);

}
