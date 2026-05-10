package com.bestRuralEvents.TicketService.dto;

public record NotificationRequest(
        Long recipientUserId,
        String title,
        String text,
        String type,
        String relatedEntityType,
        Long relatedEntityId
) {
}