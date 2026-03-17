package com.example.inventory.module_15_assignment.service;

import com.example.inventory.module_15_assignment.dto.RegisterRequest;
import com.example.inventory.module_15_assignment.model.AppUser;
import com.example.inventory.module_15_assignment.model.Role;
import com.example.inventory.module_15_assignment.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AppUser register(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("username is required");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("password is required");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("username already exists");
        }

        Role role = request.getRole() == null ? Role.ROLE_USER : request.getRole();

        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }
}

