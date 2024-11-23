package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.models.dtos.TaskDto;
import com.example.taskmanagementsystem.models.dtos.TaskFilter;
import com.example.taskmanagementsystem.models.entities.Priority;
import com.example.taskmanagementsystem.models.entities.Status;
import com.example.taskmanagementsystem.models.entities.User;
import com.example.taskmanagementsystem.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/task")
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TaskController {
    private TaskService taskService;

    @Operation(summary = "Add a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @PostMapping("/add")
    void newTask(@RequestBody @Valid TaskDto taskDto, @AuthenticationPrincipal User user) {
        taskDto.setAuthorId(user.getId());
        taskService.create(taskDto);
    }

    @Operation(summary = "Get a task by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the task",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @Operation(summary = "Get the tasks by its byAuthorId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tasks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byAuthorId")
    public List<TaskDto> getAllByAuthorId(@RequestParam Long authorId) {
        return taskService.findByAuthorId(authorId);
    }

    @Operation(summary = "Get the tasks by its byPerformerId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tasks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byPerformerId")
    public List<TaskDto> getAllByPerformerId(Long performerId) {
        return taskService.findByPerformerId(performerId);
    }

    @Operation(summary = "Get the tasks by its byAuthorName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tasks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byAuthorName")
    public List<TaskDto> getAllByAuthorName(@RequestParam String authorName) {
        return taskService.findByAuthorName(authorName);
    }

    @Operation(summary = "Get the tasks by its byPerformerName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tasks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byPerformerName")
    public List<TaskDto> getAllByPerformerName(@RequestParam String performerName) {
        return taskService.findByPerformerName(performerName);
    }

    @Operation(summary = "Get the tasks by its byAuthorEmail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tasks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byAuthorEmail")
    public List<TaskDto> getAllByAuthorEmail(@RequestParam String authorEmail) {
        return taskService.findTasksByAuthorEmail(authorEmail);
    }

    @Operation(summary = "Get the tasks by its byPerformerEmail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tasks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byPerformerEmail")
    public List<TaskDto> getAllByPerformerEmail(@RequestParam String performerEmail) {
        return taskService.findTasksByPerformerEmail(performerEmail);
    }

    @Operation(summary = "Get the tasks ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the tasks",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping()
    public List<TaskDto> getAll() {
        return taskService.findAll();
    }

    @Operation(summary = "GetAllByFilter a task by its status, header, priority")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "GetAllByFilter a task",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })
    @GetMapping("/byFilter")
    public Page<TaskDto> getAllByFilter(@RequestParam Status status,
                                        @RequestParam String header,
                                        @RequestParam Priority priority,
                                        @RequestParam int page,
                                        @RequestParam int pageSize) {

        return taskService.findAllByFilterByPageable(new TaskFilter(header, priority, status),
                PageRequest.of(page, pageSize));
    }

    @Operation(summary = "Delete a task by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete a task",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable Long id) {
        taskService.delete(id);
    }

    @Operation(summary = "Update a task by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update a task",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @PutMapping("/{id}")
    void updateTask(@RequestBody @Valid TaskDto taskDto, @PathVariable Long id) {
        taskDto.setId(id);
        taskService.update(taskDto);
    }

    @Operation(summary = "Post  a comment by task id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = " You are not authorized to access this resource",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @PostMapping("/comment/{id}")
    @PreAuthorize("@taskService.isPerformer(authentication.name, #id)")
    void addComment(@RequestBody @Valid TaskDto taskDto, @PathVariable Long id) {
        taskDto.setId(id);
        taskService.addComment(taskDto);
    }

    @Operation(summary = "Update  a status by task id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successful",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaskDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = " You are not authorized to access this resource",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @PostMapping("/status/{id}")
    @PreAuthorize("@taskService.isPerformer(authentication.name, #id)")
    void updateStatus(@RequestBody @Valid TaskDto taskDto, @PathVariable Long id) {
        taskDto.setId(id);
        taskService.updateStatus(taskDto);
    }

}
