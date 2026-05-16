package com.bestRuralEvents.PaymentService.dto;

public record NotificationRequest(
        Long recipientUserId,
        String title,
        String text,
        String type,
        String relatedEntityType,
        Long relatedEntityId
) {
}