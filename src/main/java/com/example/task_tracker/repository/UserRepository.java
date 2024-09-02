package com.example.task_tracker.repository;

import com.example.task_tracker.entity.User;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
