package com.bestRuralEvents.ReviewService.dto;

public record UpdateEventRatingRequest(
        Double averageRating,
        Integer totalReviews
) {}