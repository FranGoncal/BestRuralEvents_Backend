package com.bestRuralEvents.EventService.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDayCapacity {

    @NotNull
    private LocalDate date;

    @NotNull
    @Min(1)
    private Integer capacity;
}