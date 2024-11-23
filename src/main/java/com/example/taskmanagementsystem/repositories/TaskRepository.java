package com.example.taskmanagementsystem.repositories;

import com.example.taskmanagementsystem.models.entities.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends CrudRepository<Task, Long>, JpaSpecificationExecutor<Task> {
    List<Task> findTaskByPerformerId(Long authorId);
    List<Task> findTasksByAuthorId(Long performerId);
    List<Task> findTasksByAuthorName(String authorName);
    List<Task> findTasksByPerformerName(String performerName);
    List<Task> findTasksByAuthorEmail(String email);
    List<Task> findTasksByPerformerEmail(String email);
}
