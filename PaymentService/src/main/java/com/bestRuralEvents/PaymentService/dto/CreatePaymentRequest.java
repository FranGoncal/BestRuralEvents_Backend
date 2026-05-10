package com.bestRuralEvents.PaymentService.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CreatePaymentRequest(
        @NotNull Long eventId,
        @NotNull @Min(1) Integer quantity,
        @NotNull @DecimalMin("0.0") BigDecimal amount,
        @NotBlank String paymentMethodId,
        List<LocalDate> selectedDays
) {}