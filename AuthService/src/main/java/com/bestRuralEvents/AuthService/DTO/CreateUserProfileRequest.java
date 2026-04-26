package com.bestRuralEvents.AuthService.DTO;

import java.time.LocalDate;

public record CreateUserProfileRequest(
        Long id,
        String name,
        String email,
        LocalDate birthDate
) {}