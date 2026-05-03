package com.bestRuralEvents.ReviewService.dto;

import jakarta.validation.constraints.*;

public record CreateReviewRequest(
        @NotNull Long eventId,

        @NotNull
        @Min(1)
        @Max(5)
        Integer rating,

        @Size(max = 2000)
        String comment
) {}