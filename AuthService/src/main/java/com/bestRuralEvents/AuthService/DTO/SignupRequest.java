package com.bestRuralEvents.AuthService.DTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record SignupRequest(
        @NotBlank @Size(min = 2, max = 100) String name,
        @NotBlank @Email @Size(max = 150) String email,
        @NotNull @Past LocalDate birthDate,
        @NotBlank @Size(min = 8, max = 100) String password
) {}