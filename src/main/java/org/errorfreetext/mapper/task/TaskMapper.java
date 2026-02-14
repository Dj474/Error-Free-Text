package org.errorfreetext.mapper.task;

import org.errorfreetext.dto.task.TaskDtoIn;
import org.errorfreetext.dto.task.TaskInfoDtoOut;
import org.errorfreetext.entity.task.Task;
import org.errorfreetext.other.enums.TaskStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "sourceText", source = "text")
    Task toEntity(TaskDtoIn dto);

    default TaskInfoDtoOut toDto(Task task) {
        TaskInfoDtoOut dto = new TaskInfoDtoOut();
        dto.setId(task.getId());
        dto.setStatus(task.getStatus().toString());
        if (task.getStatus().equals(TaskStatus.COMPLETED)) {
            dto.setCorrectedText(task.getCorrectedText());
            return dto;
        }
        if (task.getStatus().equals(TaskStatus.ERROR)) {
            dto.setErrorMessage(task.getErrorMessage());
            return dto;
        }
        return dto;
    }

}
