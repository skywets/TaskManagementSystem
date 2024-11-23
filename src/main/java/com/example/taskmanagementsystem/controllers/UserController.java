package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.models.dtos.UserDto;
import com.example.taskmanagementsystem.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@PreAuthorize("hasAuthority('ACCESS_ALL')")
public class UserController {
    private UserService userService;

    @Operation(summary = "Get a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "The JWT token has expired",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })


    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @Operation(summary = "Get a user by its byName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byName")
    public UserDto getByName(@RequestParam String name) {
        return userService.findByName(name);
    }

    @Operation(summary = "Get a user by its byEmail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping("/byEmail")
    public UserDto getByEmail(@RequestParam String email) {
        return userService.findByEmail(email);
    }

    @Operation(summary = "Delete a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
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
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }

    @Operation(summary = "Get the users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the users",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized: The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @GetMapping()
    List<UserDto> getAll() {
        return userService.findAll();
    }

    @Operation(summary = "Update a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Update the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
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
    void updateUser(@RequestBody @Valid UserDto userRegistrationDto,
                    @PathVariable Long id) {
        userRegistrationDto.setId(id);
        userService.update(userRegistrationDto);
    }
}
