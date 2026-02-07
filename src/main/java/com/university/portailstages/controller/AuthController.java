package com.university.portailstages.controller;

import com.university.portailstages.dto.AuthResponse;
import com.university.portailstages.dto.LoginRequest;
import com.university.portailstages.dto.RegisterRequest;
import com.university.portailstages.entity.OffreStage;
import com.university.portailstages.entity.User;
import com.university.portailstages.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll() {
        return authService.findAll();
    }


}
