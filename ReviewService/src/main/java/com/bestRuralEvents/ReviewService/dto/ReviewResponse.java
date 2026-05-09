package com.bestRuralEvents.ReviewService.dto;


import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long eventId,
        Long userId,
        String userName,
        String eventName,
        LocalDate eventStartDate,
        LocalDate eventEndDate,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}