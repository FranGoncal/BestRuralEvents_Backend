package com.bestRuralEvents.EventService.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record MainPageResponse(
        String message,
        LocalDateTime lastUpdated,
        List<EventResponse> events
) {
}