package com.bestRuralEvents.EventService.DTO;

import java.util.List;

public record SearchEventsResponse(
        String message,
        List<EventResponse> events,
        PaginationResponse pagination
) {
}