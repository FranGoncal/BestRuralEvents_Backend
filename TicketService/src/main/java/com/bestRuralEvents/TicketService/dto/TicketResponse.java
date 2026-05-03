package com.bestRuralEvents.TicketService.dto;

import com.bestRuralEvents.TicketService.models.TicketMode;
import com.bestRuralEvents.TicketService.models.TicketStatus;
import com.bestRuralEvents.TicketService.models.TicketType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

public record TicketResponse(
        Long id,
        Long userId,
        Long eventId,
        TicketMode ticketMode,
        List<LocalDate> selectedDays,
        TicketStatus status,
        String validationToken,
        BigDecimal pricePaid,
        Currency currency,
        LocalDateTime createdAt
) {}