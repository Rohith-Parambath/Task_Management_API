package com.rohith.TaskManagem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "username is required")
    @Size(min = 2, max = 50, message = "Username must be between 3-50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Must be an valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "password must be at least 6 characters")
    private String password;

}
