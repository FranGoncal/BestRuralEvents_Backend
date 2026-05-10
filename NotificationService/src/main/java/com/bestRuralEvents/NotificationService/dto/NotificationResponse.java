package com.bestRuralEvents.NotificationService.dto;

import com.bestRuralEvents.NotificationService.models.Notification;
import com.bestRuralEvents.NotificationService.models.NotificationType;

import java.time.LocalDateTime;

public record NotificationResponse(
        Long id,
        String title,
        String text,
        LocalDateTime time,
        boolean read,
        NotificationType type
) {
    public static NotificationResponse fromEntity(Notification notification) {
        return new NotificationResponse(
                notification.getId(),
                notification.getTitle(),
                notification.getText(),
                notification.getCreatedAt(),
                notification.isRead(),
                notification.getType()
        );
    }
}