package org.errorfreetext.controller.task;

import jakarta.validation.Valid;
import org.errorfreetext.dto.task.TaskDtoIn;
import org.errorfreetext.dto.task.TaskInfoDtoOut;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/tasks")
public interface TaskController {

    @PostMapping
    void createTask(
            @Valid @RequestBody TaskDtoIn taskCreateDtoIn
    );

    @GetMapping
    TaskInfoDtoOut getTaskInfo(@PathVariable UUID taskId);

    @GetMapping("/all")
    List<UUID> getUserTasks();
}