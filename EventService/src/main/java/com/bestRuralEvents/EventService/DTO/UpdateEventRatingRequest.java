package com.bestRuralEvents.EventService.DTO;

public record UpdateEventRatingRequest(
        Double averageRating,
        Integer totalReviews
) {}