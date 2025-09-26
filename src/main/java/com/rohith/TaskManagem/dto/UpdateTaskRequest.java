package com.rohith.TaskManagem.dto;

import com.rohith.TaskManagem.model.TaskStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskRequest {

    @Size(max = 100, message = "Title must not exceed 100 characters")
    private String title;

    private String description;

    private TaskStatus status;
}
