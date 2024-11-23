package com.example.taskmanagementsystem.models.mappers;

import com.example.taskmanagementsystem.models.dtos.TaskDto;
import com.example.taskmanagementsystem.models.dtos.UserDto;
import com.example.taskmanagementsystem.models.entities.Task;
import com.example.taskmanagementsystem.models.entities.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@AllArgsConstructor
public class UserImplMapper implements MapperDto<UserDto, User, UserDto> {
    private MapperDto<TaskDto, Task, TaskDto> mapperDto;
    @Override
    public UserDto mapToDto(@Valid User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setRole(entity.getRole());
        userDto.setCreatedTasks(mapperDto.maptoDto(entity.getCreatedTasks()));
        userDto.setPerformedTasks(mapperDto.maptoDto(entity.getPerformedTasks()));
        return userDto;
    }

    @Override
    public User mapToEntity(@Valid UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setRole(dto.getRole());
        user.setCreatedTasks(mapperDto.mapToEntity(dto.getCreatedTasks()));
        user.setPerformedTasks(mapperDto.mapToEntity(dto.getPerformedTasks()));
        return user;
    }

    @Override
    public List<UserDto> maptoDto(Iterable<User> entities) {
        List<UserDto> users = new ArrayList<>();
        for (User user:entities) {
            users.add(mapToDto(user));
        }
        return users;
    }

    @Override
    public List<User> mapToEntity(Iterable<UserDto> dtos) {
        List<User> users = new ArrayList<>();
        for (UserDto userRegistrationDto:dtos) {
            users.add(mapToEntity(userRegistrationDto));
        }
        return users;
    }
}
