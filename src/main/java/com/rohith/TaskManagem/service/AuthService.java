package com.rohith.TaskManagem.service;

import com.rohith.TaskManagem.dto.AuthResponse;
import com.rohith.TaskManagem.dto.LoginRequest;
import com.rohith.TaskManagem.dto.RegisterRequest;
import com.rohith.TaskManagem.exception.EmailAlreadyExistException;
import com.rohith.TaskManagem.exception.ResourceNotFoundException;
import com.rohith.TaskManagem.exception.UsernameAlreadyExistException;
import com.rohith.TaskManagem.model.User;
import com.rohith.TaskManagem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException("Username already exists");
        }
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyExistException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();

        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsernameOrEmail(),
                        request.getPassword()
                )
        );

        Optional<User> userOpt = userRepository.findByUsername(request.getUsernameOrEmail());
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmail(request.getUsernameOrEmail());
        }

        User user = userOpt.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        String token = jwtService.generateToken(user);
        long expiresAt = jwtService.getExpirationTime();

        return new AuthResponse(token, expiresAt);
    }



}
