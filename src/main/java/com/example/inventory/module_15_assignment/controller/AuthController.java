package com.example.inventory.module_15_assignment.controller;

import com.example.inventory.module_15_assignment.dto.RegisterRequest;
import com.example.inventory.module_15_assignment.model.AppUser;
import com.example.inventory.module_15_assignment.service.UserService;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        AppUser user = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "id", user.getId(),
                        "username", user.getUsername(),
                        "role", user.getRole().name()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
    }
}

