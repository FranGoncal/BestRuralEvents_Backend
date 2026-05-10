package com.bestRuralEvents.NotificationService.dto;

import com.bestRuralEvents.NotificationService.models.DevicePlatform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeviceTokenRequest(

        @NotBlank
        String token,

        @NotNull
        DevicePlatform platform
) {
}