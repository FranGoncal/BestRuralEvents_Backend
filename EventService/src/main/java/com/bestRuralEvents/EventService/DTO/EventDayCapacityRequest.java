package com.bestRuralEvents.EventService.DTO;

import java.time.LocalDate;

public record EventDayCapacityRequest(
        LocalDate date,
        Integer capacity
) {}