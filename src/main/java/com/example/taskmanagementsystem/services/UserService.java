package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.models.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {
    void create(UserDto userDto);

    void update(UserDto userDto );

    void delete(Long id);

    UserDto findById(Long id);

    UserDto findByName(String name);
    UserDto findByEmail(String email);

    List<UserDto> findAll();

}
