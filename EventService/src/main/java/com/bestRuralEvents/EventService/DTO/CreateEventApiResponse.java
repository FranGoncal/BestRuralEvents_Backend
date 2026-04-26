package com.bestRuralEvents.EventService.DTO;

public record CreateEventApiResponse(
        boolean success,
        String message,
        EventResponse event
) {
}