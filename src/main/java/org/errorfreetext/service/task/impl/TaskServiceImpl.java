package org.errorfreetext.service.task.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.errorfreetext.dto.task.TaskDtoIn;
import org.errorfreetext.dto.task.TaskInfoDtoOut;
import org.errorfreetext.entity.task.Task;
import org.errorfreetext.entity.user.User;
import org.errorfreetext.exception.ForbiddenException;
import org.errorfreetext.mapper.task.TaskMapper;
import org.errorfreetext.repository.task.TaskRepository;
import org.errorfreetext.service.task.TaskService;
import org.errorfreetext.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Override
    @Transactional
    public void createTask(TaskDtoIn taskDtoIn) {
        User current = userService.getCurrent();

        Task task = taskMapper.toEntity(taskDtoIn);
        task.setUser(current);

        taskRepository.save(task);
    }

    @Override
    @Transactional
    public TaskInfoDtoOut getTaskInfo(UUID taskId) {
        User current = userService.getCurrent();

        Task task = taskRepository.byId(taskId);

        if (!task.getUser().equals(current)) {
            throw new ForbiddenException("No access for task");
        }

        return taskMapper.toDto(task);
    }

    @Override
    @Transactional
    public List<UUID> getUserTasks() {
        return userService.getCurrent().getTasks().stream().map(Task::getId).toList();
    }
}
