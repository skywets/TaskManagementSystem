package com.example.taskmanagementsystem.models.dtos;

import com.example.taskmanagementsystem.models.entities.Priority;
import com.example.taskmanagementsystem.models.entities.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskDto {
    private Long id;
    @NotBlank
    private String header;
    private String description;
    private String comment;
    private Status status;
    private Priority priority;
    private Long authorId;
    private Long performerId;

}
