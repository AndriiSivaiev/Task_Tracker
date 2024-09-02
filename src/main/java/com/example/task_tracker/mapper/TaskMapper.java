package com.example.task_tracker.mapper;

import com.example.task_tracker.dto.TaskDto;
import com.example.task_tracker.entity.Task;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TaskMapper {

    TaskDto toDto(Task task);


    Task toEntity(TaskDto taskDto);
}
