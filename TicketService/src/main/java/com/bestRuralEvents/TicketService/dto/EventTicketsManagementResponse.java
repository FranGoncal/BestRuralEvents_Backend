package com.bestRuralEvents.TicketService.dto;

import com.bestRuralEvents.TicketService.models.TicketMode;

import java.util.List;

public record EventTicketsManagementResponse(
        String message,
        Long eventId,
        String eventTitle,
        TicketMode ticketMode,
        Integer capacity,
        Integer totalTicketsSold,
        Integer totalTicketsAvailable,
        List<EventTicketDayStatsResponse> days,
        List<TicketResponse> tickets
) {}