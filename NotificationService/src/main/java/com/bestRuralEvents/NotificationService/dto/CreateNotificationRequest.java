package com.bestRuralEvents.NotificationService.dto;

import com.bestRuralEvents.NotificationService.models.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateNotificationRequest(

        @NotNull
        Long recipientUserId,

        @NotBlank
        @Size(max = 120)
        String title,

        @NotBlank
        @Size(max = 1000)
        String text,

        @NotNull
        NotificationType type,

        String relatedEntityType,

        Long relatedEntityId
) {
}