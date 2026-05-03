package com.bestRuralEvents.ReviewService.dto;

import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        LocalDateTime date
) {}