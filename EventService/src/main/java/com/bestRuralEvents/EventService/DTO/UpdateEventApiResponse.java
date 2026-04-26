package com.bestRuralEvents.EventService.DTO;

public record UpdateEventApiResponse(
        boolean success,
        String message,
        EventResponse event
) {
}