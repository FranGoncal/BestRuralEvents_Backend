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
        TicketEventResponse event,
        BigDecimal price,
        String refundRules,
        LocalDateTime purchaseDate,
        Boolean canCancel,
        Boolean canReview,
        Integer quantity,
        String customerName,
        String customerEmail,
        LocalDateTime createdAt,
        TicketStatus status,
        List<LocalDate> selectedDays
) {}