package com.example.task_tracker.service;

import com.example.task_tracker.dto.TaskDto;
import com.example.task_tracker.dto.UserDto;
import com.example.task_tracker.entity.Task;
import com.example.task_tracker.entity.User;
import com.example.task_tracker.mapper.UserMapper;
import com.example.task_tracker.repository.TaskRepository;
import com.example.task_tracker.mapper.TaskMapper;
import com.example.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TaskMapper taskMapper;

    public Flux<TaskDto> findAllTasks() {
        return taskRepository.findAll()
                .flatMap(this::enrichTaskWithUsers);
    }

    public Mono<TaskDto> findTaskById(String id) {
        return taskRepository.findById(id)
                .flatMap(this::enrichTaskWithUsers);
    }

    private Mono<TaskDto> enrichTaskWithUsers(Task task) {
        Mono<User> authorMono = userRepository.findById(task.getAuthorId());
        Mono<User> assigneeMono = userRepository.findById(task.getAssigneeId());
        Flux<User> observersFlux = userRepository.findAllById(task.getObserverIds());

        return Mono.zip(authorMono, assigneeMono, observersFlux.collectList())
                .map(tuple -> {
                    TaskDto taskDto = taskMapper.toDto(task); // Map Task to TaskDTO
                    taskDto.setAuthor(tuple.getT1() != null ? userMapper.toDto(tuple.getT1()) : null);
                    taskDto.setAssignee(tuple.getT2() != null ? userMapper.toDto(tuple.getT2()) : null);
                    taskDto.setObservers(new HashSet<>(tuple.getT3().stream()
                            .map(userMapper::toDto)
                            .toList()));
                    return taskDto;
                });
    }

    public Mono<TaskDto> createTask(TaskDto taskDTO) {
        Task task = taskMapper.toEntity(taskDTO);
        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        return taskRepository.save(task)
                .map(taskMapper::toDto);
    }

    public Mono<TaskDto> updateTask(String id, TaskDto taskDto) {
        return taskRepository.findById(id)
                .flatMap(existingTask -> {
                    existingTask.setName(taskDto.getName());
                    existingTask.setDescription(taskDto.getDescription());
                    existingTask.setStatus(taskDto.getStatus());
                    existingTask.setAssigneeId(taskDto.getAssigneeId());
                    existingTask.setObserverIds(taskDto.getObserverIds());
                    existingTask.setUpdatedAt(Instant.now());
                    return taskRepository.save(existingTask);
                })
                .map(taskMapper::toDto);
    }

    public Mono<TaskDto> addObserver(String taskId, String observerId) {
        return taskRepository.findById(taskId)
                .flatMap(task -> {
                    task.getObserverIds().add(observerId);
                    return taskRepository.save(task);
                })
                .map(taskMapper::toDto);
    }

    public Mono<Void> deleteTaskById(String id) {
        return taskRepository.deleteById(id);
    }
}