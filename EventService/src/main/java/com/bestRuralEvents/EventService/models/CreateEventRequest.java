package com.bestRuralEvents.EventService.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class CreateEventRequest {
    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String location;

    @NotNull
    private List<EventDate> dates;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal price;

    @NotNull
    private String category;
}