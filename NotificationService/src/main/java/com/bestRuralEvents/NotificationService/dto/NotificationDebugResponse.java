package com.bestRuralEvents.NotificationService.dto;

import com.bestRuralEvents.NotificationService.models.Notification;
import com.bestRuralEvents.NotificationService.models.NotificationType;

import java.time.LocalDateTime;

public record NotificationDebugResponse(
        Long id,
        Long recipientUserId,
        String title,
        String text,
        NotificationType type,
        boolean read,
        boolean deleted,
        LocalDateTime createdAt,
        String relatedEntityType,
        Long relatedEntityId
) {
    public static NotificationDebugResponse fromEntity(Notification notification) {
        return new NotificationDebugResponse(
                notification.getId(),
                notification.getRecipientUserId(),
                notification.getTitle(),
                notification.getText(),
                notification.getType(),
                notification.isRead(),
                notification.isDeleted(),
                notification.getCreatedAt(),
                notification.getRelatedEntityType(),
                notification.getRelatedEntityId()
        );
    }
}