package com.bestRuralEvents.PaymentService.dto;

public record PaymentResponse(
        boolean success,
        String message,
        String paymentReference,
        Long ticketId,
        String bookingReference
) {}