package com.bestRuralEvents.TicketService.dto;

import com.bestRuralEvents.TicketService.models.TicketMode;
import com.bestRuralEvents.TicketService.models.TicketType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;


public record EventResponse(
        Long id,
        String title,
        String description,
        Long organizerId,
        String status,
        BigDecimal price,
        Currency currency,
        String location,
        LocalDate startDate,
        LocalDate endDate,
        List<String> images,
        TicketMode ticketMode,
        Double averageRating,
        Integer totalReviews,
        Boolean refundable,
        Integer refundDeadlineDays,
        String refundPolicy,

        Integer capacity,
        List<EventDailyCapacityResponse> dailyCapacities
) {}