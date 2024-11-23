package com.example.taskmanagementsystem.services.serviceImpl;

import com.example.taskmanagementsystem.models.dtos.TaskDto;
import com.example.taskmanagementsystem.models.dtos.TaskFilter;
import com.example.taskmanagementsystem.models.entities.Task;
import com.example.taskmanagementsystem.models.mappers.MapperDto;
import com.example.taskmanagementsystem.repositories.TaskRepository;
import com.example.taskmanagementsystem.repositories.UserRepository;
import com.example.taskmanagementsystem.services.TaskService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("taskService")
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;
    private MapperDto<TaskDto, Task, TaskDto> mapperDto;
    private UserRepository userRepository;

    @Override
    public void create(TaskDto taskDto) {
        Task task = new Task();
        Optional.ofNullable(taskDto.getPerformerId())
                .flatMap(userRepository::findById)
                .ifPresent(task::setPerformer);
        Optional.ofNullable(taskDto.getAuthorId())
                .flatMap(userRepository::findById)
                .ifPresentOrElse(task::setAuthor,
                        () -> {
                            throw new RuntimeException("author not found");
                        });
        task.setHeader(taskDto.getHeader());
        task.setDescription(taskDto.getDescription());
        task.setComment(taskDto.getComment());
        task.setStatus(taskDto.getStatus());
        task.setPriority(taskDto.getPriority());
        taskRepository.save(task);
    }

    @Override
    public void update(TaskDto taskDto) {
        Task task = getByIdOrElseThrow(taskDto.getId());
        task.setHeader(taskDto.getHeader());
        task.setDescription(taskDto.getDescription());
        task.setComment(taskDto.getComment());
        task.setStatus(taskDto.getStatus());
        task.setPriority(task.getPriority());
        Optional.ofNullable(taskDto.getPerformerId())
                .flatMap(userRepository::findById)
                .ifPresent(task::setPerformer);
        taskRepository.save(task);
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public TaskDto findById(Long id) {
        return mapperDto.mapToDto(getByIdOrElseThrow(id));
    }

    @Override
    public List<TaskDto> findAll() {
        return mapperDto.maptoDto(taskRepository.findAll());
    }

    @Override
    public Page<TaskDto> findAllByFilterByPageable(TaskFilter filter, Pageable pageable) {
        return taskRepository.findAll((Specification<Task>) (root, query, cb) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            if (filter.header() != null && !filter.header().isBlank()) {
                                predicates.add(cb.equal(root.get("header"), filter.header()));
                            }
                            if (filter.priority() != null && filter.priority().describeConstable().isPresent()) {
                                predicates.add(cb.equal(root.get("priority"), String.valueOf(filter.priority())));
                            }
                            if (filter.status() != null && filter.status().describeConstable().isPresent()) {
                                predicates.add(cb.equal(root.get("status"), String.valueOf(filter.status())));
                            }
                            return cb.and(predicates.toArray(Predicate[]::new));
                        },
                        pageable)
                .map(task -> mapperDto.mapToDto(task));
    }

    @Override
    public List<TaskDto> findByAuthorId(Long authorId) {
        return mapperDto.maptoDto(taskRepository.findTasksByAuthorId(authorId));
    }

    @Override
    public List<TaskDto> findByPerformerId(Long performerId) {
        return mapperDto.maptoDto(taskRepository.findTaskByPerformerId(performerId));
    }

    @Override
    public List<TaskDto> findByAuthorName(String authorName) {
        return mapperDto.maptoDto(taskRepository.findTasksByAuthorName(authorName));
    }

    @Override
    public List<TaskDto> findByPerformerName(String performerName) {
        return mapperDto.maptoDto(taskRepository.findTasksByPerformerName(performerName));
    }

    @Override
    public List<TaskDto> findTasksByAuthorEmail(String email) {
        return mapperDto.maptoDto(taskRepository.findTasksByAuthorEmail(email));
    }

    @Override
    public List<TaskDto> findTasksByPerformerEmail(String email) {
        return mapperDto.maptoDto(taskRepository.findTasksByPerformerEmail(email));
    }

    @Override
    public boolean isPerformer(String userName, long taskId) {
        return taskRepository.findById(taskId)
                .map(Task::getPerformer)
                .map(user -> Objects.equals(user.getName(), userName))
                .orElse(false);
    }

    @Override
    public void addComment(TaskDto taskDto) {
        Task task = getByIdOrElseThrow(taskDto.getId());
        task.setComment(taskDto.getComment());
        taskRepository.save(task);
    }

    @Override
    public void updateStatus(TaskDto taskDto) {
        Task task = getByIdOrElseThrow(taskDto.getId());
        task.setStatus(taskDto.getStatus());
        taskRepository.save(task);
    }

    private Task getByIdOrElseThrow(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("The task doesn't exist"));
    }
}
