package org.errorfreetext.repository.task;

import org.errorfreetext.entity.task.Task;
import org.errorfreetext.exception.NotFoundException;
import org.errorfreetext.other.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    default Task byId(UUID id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Task with id " + id + " not found"));
    }

    List<Task> findByStatus(TaskStatus status);

}
