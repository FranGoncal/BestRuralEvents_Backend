package com.bestRuralEvents.EventService.DTO;

import com.bestRuralEvents.EventService.models.EventCategory;
import com.bestRuralEvents.EventService.models.EventStatus;
import com.bestRuralEvents.EventService.models.TicketMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;

public record EventResponse(
        Long id,
        String title,
        String description,
        Long organizerId,
        Integer capacity,
        EventStatus status,
        EventCategory category,
        BigDecimal price,
        Currency currency,
        String location,
        LocalDate startDate,
        LocalDate endDate,
        List<String> images,
        TicketMode ticketMode,
        Boolean refundable,
        Integer refundDeadlineDays,
        String refundPolicy
) {}