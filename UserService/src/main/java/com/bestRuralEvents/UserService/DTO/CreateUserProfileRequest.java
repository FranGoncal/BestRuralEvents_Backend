package com.bestRuralEvents.UserService.DTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUserProfileRequest(
        @NotNull Long id,

        @NotBlank
        @Size(min = 2, max = 100)
        String name,

        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @NotNull
        @Past
        LocalDate birthDate
) {}