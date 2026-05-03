package com.bestRuralEvents.EventService.DTO;

import java.util.List;

public record FavoriteEventsResponse(
        String message,
        List<EventResponse> events
) {}