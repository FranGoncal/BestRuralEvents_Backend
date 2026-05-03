package com.bestRuralEvents.ReviewService.dto;


import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long eventId,
        Long userId,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}