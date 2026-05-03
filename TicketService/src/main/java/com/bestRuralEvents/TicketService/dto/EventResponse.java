package com.bestRuralEvents.TicketService.dto;

import com.bestRuralEvents.TicketService.models.TicketMode;
import com.bestRuralEvents.TicketService.models.TicketType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

public record EventResponse(
        Long id,
        String title,
        BigDecimal price,
        String currency,
        TicketMode ticketMode,
        LocalDate startDate,
        LocalDate endDate,
        Boolean refundable,
        Integer refundDeadlineDays,
        String refundPolicy
) {}