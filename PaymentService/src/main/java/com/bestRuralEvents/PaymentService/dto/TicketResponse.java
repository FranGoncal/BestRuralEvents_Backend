package com.bestRuralEvents.PaymentService.dto;

public record TicketResponse(
        Long id,
        Long eventId,
        Long userId,
        Integer quantity,
        String customerName,
        String customerEmail,
        String bookingReference,
        String status
) {}