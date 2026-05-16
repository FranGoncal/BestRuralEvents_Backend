package com.bestRuralEvents.ContentService.dto;

import com.bestRuralEvents.ContentService.models.HelpMessageStatus;

import java.time.LocalDateTime;


public record HelpMessageAdminResponse(
        Long id,
        String userId,
        String email,
        String subject,
        String message,
        HelpMessageStatus status,
        LocalDateTime createdAt
) {
}