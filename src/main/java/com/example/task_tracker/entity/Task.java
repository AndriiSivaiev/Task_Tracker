package com.example.task_tracker.entity;



import com.example.task_tracker.enumStatus.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "tasks")
public class Task {

    @Id
    private String id;

    private String name;
    private String description;

    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;

    private String authorId;
    private String assigneeId;
    private Set<String> observerIds;


    private transient User author;
    private transient User assignee;
    private transient Set<User> observers;
}
