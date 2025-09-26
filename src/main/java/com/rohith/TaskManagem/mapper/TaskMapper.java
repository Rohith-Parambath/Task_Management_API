package com.rohith.TaskManagem.mapper;

import com.rohith.TaskManagem.dto.TaskResponse;
import com.rohith.TaskManagem.model.Task;

public class TaskMapper {
    public static TaskResponse response(Task task) {
        TaskResponse response = TaskResponse.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();

        return response;
    }
}
