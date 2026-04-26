package com.bestRuralEvents.EventService.DTO;

import com.bestRuralEvents.EventService.models.EventCategory;
import com.bestRuralEvents.EventService.models.EventStatus;
import com.bestRuralEvents.EventService.models.TicketMode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record EventResponse(
        Long id,
        String title,
        String description,
        Long organizerId,
        EventStatus status,
        EventCategory category,
        BigDecimal price,
        String currency,
        String location,
        LocalDate startDate,
        LocalDate endDate,
        List<String> images,
        TicketMode ticketMode,
        Double averageRating,
        Integer totalReviews,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime publishedAt
) {
}