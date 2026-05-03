package com.bestRuralEvents.TicketService.dto;

public record TicketValidationResponse(
        Boolean valid,
        Long ticketId,
        Long userId,
        Long eventId,
        String qrToken
) {}