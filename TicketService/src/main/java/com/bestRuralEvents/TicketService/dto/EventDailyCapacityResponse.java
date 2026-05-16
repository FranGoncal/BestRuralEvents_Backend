package com.bestRuralEvents.TicketService.dto;

import java.time.LocalDate;

public record EventDailyCapacityResponse(
        LocalDate date,
        Integer capacity
) {}