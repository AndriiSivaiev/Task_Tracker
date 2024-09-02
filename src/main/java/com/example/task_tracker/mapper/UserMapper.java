package com.example.task_tracker.mapper;


import com.example.task_tracker.dto.UserDto;
import com.example.task_tracker.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {


        UserDto toDto(User user);

        User toEntity(UserDto userDto);
}
