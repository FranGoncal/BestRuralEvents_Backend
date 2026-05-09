package com.bestRuralEvents.ReviewService.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EventResponse(
        Long id,
        String title,
        LocalDate startDate,
        LocalDate endDate
) {}