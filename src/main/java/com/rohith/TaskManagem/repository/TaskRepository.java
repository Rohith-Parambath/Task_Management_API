package com.rohith.TaskManagem.repository;

import com.rohith.TaskManagem.model.Task;
import com.rohith.TaskManagem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    Optional<Task> findByIdAndUserUsername(Long id, String username);
}
