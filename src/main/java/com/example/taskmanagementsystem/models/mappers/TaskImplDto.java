package com.example.taskmanagementsystem.models.mappers;

import com.example.taskmanagementsystem.models.dtos.TaskDto;
import com.example.taskmanagementsystem.models.entities.Task;
import com.example.taskmanagementsystem.models.entities.User;
import com.example.taskmanagementsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class TaskImplDto implements MapperDto<TaskDto, Task, TaskDto> {
    private UserRepository userRepository;

    @Override
    public TaskDto mapToDto(Task entity) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(entity.getId());
        taskDto.setHeader(entity.getHeader());
        taskDto.setDescription(entity.getDescription());
        taskDto.setComment(entity.getComment());
        taskDto.setStatus(entity.getStatus());
        taskDto.setPriority(entity.getPriority());
        taskDto.setAuthorId(entity.getAuthor().getId());
        Optional.ofNullable(entity.getPerformer())
                .map(User::getId)
                .ifPresent(taskDto::setPerformerId);
        return taskDto;
    }

    @Override
    public Task mapToEntity(TaskDto dto) {
        Task task = new Task();
        User author = new User();
        author.setId(dto.getAuthorId());
        author.setId(dto.getPerformerId());
        task.setHeader(dto.getHeader());
        task.setDescription(dto.getDescription());
        task.setComment(dto.getComment());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setAuthor(author);
        Optional.ofNullable(dto.getPerformerId())
                .map((Long id) -> userRepository.findById(id))
                .ifPresent(performer1 -> {
                    task.setPerformer(performer1.get());
                });
        return task;
    }

    @Override
    public List<TaskDto> maptoDto(Iterable<Task> entities) {
        List<TaskDto> taskDtos = new ArrayList<>();
        for (Task task : entities) {
            taskDtos.add(mapToDto(task));
        }
        return taskDtos;
    }

    @Override
    public List<Task> mapToEntity(Iterable<TaskDto> dtos) {
        List<Task> tasks = new ArrayList<>();
        for (TaskDto taskDto : dtos) {
            tasks.add(mapToEntity(taskDto));
        }
        return tasks;
    }
}
