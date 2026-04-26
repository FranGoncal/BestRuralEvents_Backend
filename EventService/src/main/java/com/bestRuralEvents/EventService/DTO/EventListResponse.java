package com.bestRuralEvents.EventService.DTO;

import java.util.List;

public record EventListResponse(
        String message,
        List<EventResponse> events
) {
}