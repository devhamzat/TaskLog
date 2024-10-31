package org.hae.tasklogue.repository.taskrepository;

import org.hae.tasklogue.entity.applicationUser.ApplicationUser;
import org.hae.tasklogue.entity.tasks.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {

    Optional<Task> findByTaskId(String taskId);

    @Query(value = "select t from Task  t where t.createdBy = :user")
    Page<Task> findAllByCreatedBy(@Param("user") ApplicationUser user, Pageable pageable);

}
