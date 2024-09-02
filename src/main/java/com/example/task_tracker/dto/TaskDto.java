package com.example.task_tracker.dto;

import com.example.task_tracker.enumStatus.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    private String id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title must be less than or equal to 100 characters")
    private String name;

    private String description;

    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;

    @NotNull(message = "Author ID is mandatory")
    private String authorId;

    @NotNull(message = "Assignee ID is mandatory")
    private String assigneeId;

    private Set<String> observerIds;


    private UserDto author;
    private UserDto assignee;
    private Set<UserDto> observers;
}
