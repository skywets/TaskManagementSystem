package com.example.taskmanagementsystem.services.serviceImpl;

import com.example.taskmanagementsystem.models.dtos.UserDto;
import com.example.taskmanagementsystem.models.entities.Role;
import com.example.taskmanagementsystem.models.entities.User;
import com.example.taskmanagementsystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public User signup(UserDto userDto) {
        User user = new  User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public User authenticate(UserDto userDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDto.getEmail(),
                      userDto.getPassword()
                )
        );

        return userRepository.findByEmail(userDto.getEmail())
                .orElseThrow();
    }
}
