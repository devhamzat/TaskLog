package org.hae.tasklogue.repository.taskrepository;

import org.hae.tasklogue.entity.tasks.TaskVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskVersionRepository extends JpaRepository<TaskVersion, UUID> {
}
