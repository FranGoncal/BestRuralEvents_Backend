package com.bestRuralEvents.TicketService.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

public record TicketEventResponse(
        Long id,
        String title,
        String location,
        LocalDate date,
        String imageUrl,
        BigDecimal price,
        Double averageRating,
        Integer totalReviews,
        String description
) {}