package com.bestRuralEvents.AuthService.Controller;

import com.bestRuralEvents.AuthService.DTO.LoginRequest;
import com.bestRuralEvents.AuthService.DTO.LoginResponse;
import com.bestRuralEvents.AuthService.DTO.SignupRequest;
import com.bestRuralEvents.AuthService.DTO.SignupResponse;
import com.bestRuralEvents.AuthService.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}