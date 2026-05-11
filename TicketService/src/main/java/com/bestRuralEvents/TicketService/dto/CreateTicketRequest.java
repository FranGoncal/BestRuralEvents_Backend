package com.bestRuralEvents.TicketService.dto;

import java.time.LocalDate;
import java.util.List;

public record CreateTicketRequest(
        Long eventId,
        Integer quantity,
        List<LocalDate> selectedDays
) {}