package com.example.taskmanagementsystem.controllers;

import com.example.taskmanagementsystem.models.dtos.LoginResponse;
import com.example.taskmanagementsystem.models.dtos.UserDto;
import com.example.taskmanagementsystem.models.entities.User;
import com.example.taskmanagementsystem.security.JwtService;
import com.example.taskmanagementsystem.services.serviceImpl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Operation(summary = "Registration a user by name, email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Incorrect request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody @Valid UserDto registerUserDto) {
        authenticationService.signup(registerUserDto);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Authentication a user by email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration the user",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "401", description = "The username or password is incorrect",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Unknown internal server error",
                    content = @Content)
    })

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid UserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}
