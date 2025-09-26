package com.rohith.TaskManagem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "username or Email is required")
    private String usernameOrEmail;

    @NotBlank(message = "password is required")
    private String password;
}
