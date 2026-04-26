package com.bestRuralEvents.AuthService.DTO;

public record SignupResponse(
        String message,
        Long userId,
        String email
) {}