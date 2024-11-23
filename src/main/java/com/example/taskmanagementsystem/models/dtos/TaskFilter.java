package com.example.taskmanagementsystem.models.dtos;

import com.example.taskmanagementsystem.models.entities.Priority;
import com.example.taskmanagementsystem.models.entities.Status;

public record TaskFilter(
         String header,
         Priority priority,
         Status status) {

}
