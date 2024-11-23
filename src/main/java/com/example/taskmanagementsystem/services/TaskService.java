package com.example.taskmanagementsystem.services;

import com.example.taskmanagementsystem.models.dtos.TaskDto;
import com.example.taskmanagementsystem.models.dtos.TaskFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    void create(TaskDto taskDto);
    void update(TaskDto taskDto);
    void delete(Long id);
    TaskDto findById(Long id);
    List<TaskDto> findAll();
    Page<TaskDto> findAllByFilterByPageable(TaskFilter filter, Pageable pageable);
    List<TaskDto> findByAuthorId(Long authorId);
    List<TaskDto> findByPerformerId(Long performerId);
    List<TaskDto> findByAuthorName(String authorName);
    List<TaskDto> findByPerformerName(String performerName);
    List<TaskDto> findTasksByAuthorEmail(String email);
    List<TaskDto> findTasksByPerformerEmail(String email);

    boolean isPerformer(String userName, long taskId);

    void addComment(TaskDto taskDto);
    void updateStatus(TaskDto taskDto);
}
