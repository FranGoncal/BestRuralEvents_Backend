package com.bestRuralEvents.TicketService.dto;

import java.math.BigDecimal;
import java.util.Currency;

public record PaymentRequest(
        Long userId,
        Long eventId,
        BigDecimal amount,
        Currency currency
) {}