package com.bestRuralEvents.PaymentService.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateTicketRequest(
        Long eventId,
        List<LocalDate> selectedDays
) {}