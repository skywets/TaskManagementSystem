package com.example.taskmanagementsystem.repositories;

import com.example.taskmanagementsystem.models.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);

}
