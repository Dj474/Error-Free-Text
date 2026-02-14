package org.errorfreetext.controller.task.impl;

import lombok.RequiredArgsConstructor;
import org.errorfreetext.controller.task.TaskController;
import org.errorfreetext.dto.task.TaskDtoIn;
import org.errorfreetext.dto.task.TaskInfoDtoOut;
import org.errorfreetext.service.task.TaskService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskService taskService;

    @Override
    public void createTask(TaskDtoIn taskCreateDtoIn) {
        taskService.createTask(taskCreateDtoIn);
    }

    @Override
    public TaskInfoDtoOut getTaskInfo(UUID taskId) {
        return taskService.getTaskInfo(taskId);
    }

    @Override
    public List<UUID> getUserTasks() {
        return taskService.getUserTasks();
    }
}