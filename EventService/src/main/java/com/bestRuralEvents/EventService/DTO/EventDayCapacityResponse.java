package com.bestRuralEvents.EventService.DTO;


import java.time.LocalDate;

public record EventDayCapacityResponse(
        LocalDate date,
        Integer capacity
) {}