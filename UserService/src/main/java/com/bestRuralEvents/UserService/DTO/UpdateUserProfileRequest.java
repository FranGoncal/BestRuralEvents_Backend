package com.bestRuralEvents.UserService.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserProfileRequest(
        @NotBlank
        @Size(min = 2, max = 100)
        String name,

        @NotNull
        @Past
        LocalDate birthDate
) {}