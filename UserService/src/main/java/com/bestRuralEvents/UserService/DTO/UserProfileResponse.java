package com.bestRuralEvents.UserService.DTO;

import java.time.LocalDate;

public record UserProfileResponse(
        Long id,
        String name,
        String email,
        LocalDate birthDate
) {}