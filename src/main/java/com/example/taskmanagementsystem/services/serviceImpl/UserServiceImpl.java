package com.example.taskmanagementsystem.services.serviceImpl;

import com.example.taskmanagementsystem.exceptions.NotFoundException;
import com.example.taskmanagementsystem.models.dtos.UserDto;
import com.example.taskmanagementsystem.models.entities.User;
import com.example.taskmanagementsystem.models.mappers.MapperDto;
import com.example.taskmanagementsystem.repositories.UserRepository;
import com.example.taskmanagementsystem.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private MapperDto<UserDto, User, UserDto> mapperDto;

    @Override
    public void create(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }

    @Override
    public void update(UserDto userDto) {
        User user = getByIdOrElseThrow(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findById(Long id) {
        return mapperDto.mapToDto(getByIdOrElseThrow(id));
    }

    @Override
    public UserDto findByName(String name) {
        return mapperDto.mapToDto(userRepository.findByName(name).orElseThrow());
    }

    @Override
    public UserDto findByEmail(String email) {
        return mapperDto.mapToDto(userRepository.findByEmail(email).orElseThrow());
    }

    @Override
    public List<UserDto> findAll() {
        return mapperDto.maptoDto(userRepository.findAll());
    }

    private User getByIdOrElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("The User does not exist"));
    }
}
