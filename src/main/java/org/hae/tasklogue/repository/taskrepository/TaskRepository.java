package org.hae.tasklogue.repository.taskrepository;

import org.hae.tasklogue.entity.tasks.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String> {
}
