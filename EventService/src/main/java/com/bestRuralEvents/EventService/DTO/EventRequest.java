package com.bestRuralEvents.EventService.DTO;

import com.bestRuralEvents.EventService.models.EventCategory;
import com.bestRuralEvents.EventService.models.TicketMode;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record EventRequest(
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotBlank(message = "Location is required")
        String location,

        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.0", message = "Price cannot be negative")
        BigDecimal price,

        EventCategory category,

        TicketMode ticketMode,

        List<String> images
) {
}