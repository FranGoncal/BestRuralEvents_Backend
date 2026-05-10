package com.bestRuralEvents.PaymentService.dto;

public record NotificationRequest(
        Long userId,
        String title,
        String message
) {}