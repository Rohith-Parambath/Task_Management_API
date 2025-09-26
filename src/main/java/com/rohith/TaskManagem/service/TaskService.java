package com.rohith.TaskManagem.service;

import com.rohith.TaskManagem.dto.CreateTaskRequest;
import com.rohith.TaskManagem.dto.TaskResponse;
import com.rohith.TaskManagem.dto.UpdateTaskRequest;
import com.rohith.TaskManagem.exception.ResourceNotFoundException;
import com.rohith.TaskManagem.mapper.TaskMapper;
import com.rohith.TaskManagem.model.Task;
import com.rohith.TaskManagem.model.User;
import com.rohith.TaskManagem.repository.TaskRepository;
import com.rohith.TaskManagem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponse createTask(CreateTaskRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus())
                .user(user)
                .build();

        Task saved = taskRepository.save(task);

        return TaskMapper.response(saved);
    }

    public List<TaskResponse> taskList(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return  taskRepository.findByUser(user)
                .stream()
                .map(TaskMapper::response)
                .collect(Collectors.toList());
    }

    public TaskResponse updateTask(Long id, UpdateTaskRequest request, String username) {
        Task task = taskRepository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found"));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        Task saved = taskRepository.save(task);
        return TaskMapper.response(saved);
    }

    public void deleteTask(Long id, String username) {
        Task task = taskRepository.findByIdAndUserUsername(id, username)
                .orElseThrow(() -> new ResourceNotFoundException("Task Not Found"));

        taskRepository.delete(task);
    }
}
