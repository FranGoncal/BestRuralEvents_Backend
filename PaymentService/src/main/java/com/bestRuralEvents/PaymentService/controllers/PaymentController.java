package com.bestRuralEvents.PaymentService.controllers;

import com.bestRuralEvents.PaymentService.dto.CreatePaymentRequest;
import com.bestRuralEvents.PaymentService.dto.PaymentResponse;
import com.bestRuralEvents.PaymentService.services.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentResponse processPayment(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CreatePaymentRequest request
    ) {
        return paymentService.processPayment(userId, request);
    }
}