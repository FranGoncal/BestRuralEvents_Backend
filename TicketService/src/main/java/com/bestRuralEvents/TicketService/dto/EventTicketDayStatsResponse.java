package com.bestRuralEvents.TicketService.dto;

import java.time.LocalDate;

public record EventTicketDayStatsResponse(
        LocalDate date,
        Integer capacity,
        Integer ticketsSold,
        Integer ticketsAvailable
) {}