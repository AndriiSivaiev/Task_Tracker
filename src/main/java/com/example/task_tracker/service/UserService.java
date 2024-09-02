package com.example.task_tracker.service;


import com.example.task_tracker.dto.UserDto;
import com.example.task_tracker.entity.User;
import com.example.task_tracker.mapper.UserMapper;
import com.example.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;

@Service
@Slf4j
public class UserService {


@Autowired
    private static UserRepository userRepository;
@Autowired
    private static UserMapper mapper;





    public Flux<UserDto> findAll(){
        log.info("Fetching all users");
        return userRepository.findAll()
                .map(mapper::toDto);


    }

    public Mono<UserDto>finById(String id){
        log.info(" User id: {}", id);
        return userRepository.findById(id)
                .map(mapper::toDto);


    }

    public Mono<UserDto> createUser(UserDto userDto){
        log.info("User create");
        User user = mapper.toEntity(userDto);
        return userRepository.save(user)
                .map(mapper::toDto);
    }

    public Mono<UserDto> updateUser(String id, UserDto userDTO) {
        log.info("Update user with id: {}", id);
        return userRepository.findById(id)
                .flatMap(existingUser -> {
                    existingUser.setUsername(userDTO.getUsername());
                    existingUser.setEmail(userDTO.getEmail());
                    return userRepository.save(existingUser);
                })
                .map(mapper::toDto);
    }

    public Mono<Void> deleteUser (String id){
        log.info("Delete: {}", id);
        return userRepository.deleteById(id);
    }
}
