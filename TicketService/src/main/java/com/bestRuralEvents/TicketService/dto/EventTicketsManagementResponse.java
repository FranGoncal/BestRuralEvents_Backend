package com.bestRuralEvents.TicketService.dto;

import java.util.List;

public record EventTicketsManagementResponse(
        String message,
        Integer capacity,
        Integer ticketsSold,
        Integer ticketsAvailable,
        List<TicketResponse> tickets
) {}