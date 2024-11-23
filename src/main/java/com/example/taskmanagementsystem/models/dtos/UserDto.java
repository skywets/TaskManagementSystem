package com.example.taskmanagementsystem.models.dtos;

import com.example.taskmanagementsystem.models.entities.Role;
import com.example.taskmanagementsystem.validation.constraint.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;
    @Password
    @NotBlank(message = "Password cannot be blank")
    private String password;
    private Role role;
    private List<TaskDto> createdTasks;
    private List<TaskDto> performedTasks;
}
