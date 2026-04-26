package com.bestRuralEvents.EventService.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventSearchCriteria(
        String query,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Double minRating,
        String activityType,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer page,
        Integer pageSize,
        String sort
) {}