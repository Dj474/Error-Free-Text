package org.errorfreetext.service.task;

import org.errorfreetext.dto.task.TaskDtoIn;
import org.errorfreetext.dto.task.TaskInfoDtoOut;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    void createTask(TaskDtoIn taskDtoIn);

    TaskInfoDtoOut getTaskInfo(UUID taskId);

    List<UUID> getUserTasks();

}
