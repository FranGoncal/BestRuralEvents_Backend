package com.bestRuralEvents.ReviewService.dto;


public record RatingSummaryResponse(
        Long eventId,
        Double averageRating,
        Long totalReviews
) {}