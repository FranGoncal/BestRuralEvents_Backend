package com.bestRuralEvents.NotificationService.dto;

import com.bestRuralEvents.NotificationService.models.DevicePlatform;
import com.bestRuralEvents.NotificationService.models.DeviceToken;

import java.time.LocalDateTime;

public record DeviceTokenDebugResponse(
        Long id,
        Long userId,
        String token,
        DevicePlatform platform,
        boolean active,
        LocalDateTime createdAt,
        LocalDateTime lastUsedAt
) {
    public static DeviceTokenDebugResponse fromEntity(DeviceToken deviceToken) {
        return new DeviceTokenDebugResponse(
                deviceToken.getId(),
                deviceToken.getUserId(),
                deviceToken.getToken(),
                deviceToken.getPlatform(),
                deviceToken.isActive(),
                deviceToken.getCreatedAt(),
                deviceToken.getLastUsedAt()
        );
    }
}