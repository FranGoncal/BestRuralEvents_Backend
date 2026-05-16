package com.bestRuralEvents.EventService.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Embeddable
public class EventDayCapacity {

    @NotNull
    private LocalDate date;

    @NotNull
    @Min(1)
    private Integer capacity;

    public EventDayCapacity(LocalDate date, Integer capacity) {
        this.date = date;
        this.capacity = capacity;
    }

    public EventDayCapacity() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}