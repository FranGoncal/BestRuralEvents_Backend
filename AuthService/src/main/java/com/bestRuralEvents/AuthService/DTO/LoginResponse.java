package com.bestRuralEvents.AuthService.DTO;

public record LoginResponse(
        String message,
        Long userId,
        String email,
        String role,
        String accessToken,
        String tokenType
) {}