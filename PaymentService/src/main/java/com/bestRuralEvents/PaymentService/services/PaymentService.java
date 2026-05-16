package com.bestRuralEvents.PaymentService.services;

import com.bestRuralEvents.PaymentService.dto.*;
import com.bestRuralEvents.PaymentService.models.Payment;
import com.bestRuralEvents.PaymentService.models.PaymentStatus;
import com.bestRuralEvents.PaymentService.proxy.ProxyNotification;
import com.bestRuralEvents.PaymentService.proxy.ProxyTicket;
import com.bestRuralEvents.PaymentService.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProxyTicket proxyTicket;
    private final ProxyNotification notificationClient;

    public PaymentService(
            PaymentRepository paymentRepository,
            ProxyTicket proxyTicket,
            ProxyNotification ProxyNotification
    ) {
        this.paymentRepository = paymentRepository;
        this.proxyTicket = proxyTicket;
        this.notificationClient = ProxyNotification;
    }

    @Transactional
    public PaymentResponse processPayment(Long userId, CreatePaymentRequest request) {
        Payment payment = new Payment();

        payment.setUserId(userId);
        payment.setEventId(request.eventId());
        payment.setQuantity(request.quantity());
        payment.setAmount(request.amount());
        payment.setPaymentMethodId(request.paymentMethodId());
        payment.setPaymentReference("PAY-" + UUID.randomUUID());
        payment.setStatus(PaymentStatus.PENDING);

        paymentRepository.save(payment);

        simulatePaymentDelay();

        boolean paymentSucceeded = simulatePaymentResult(request.paymentMethodId());

        if (!paymentSucceeded) {
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Simulated payment failure.");
            paymentRepository.save(payment);

            return new PaymentResponse(
                    false,
                    "Payment failed. Please try another payment method.",
                    payment.getPaymentReference(),
                    null,
                    null
            );
        }

        payment.setStatus(PaymentStatus.SUCCEEDED);
        paymentRepository.save(payment);

        CreateTicketRequest ticketRequest = new CreateTicketRequest(
                request.eventId(),
                request.quantity(),
                request.selectedDays()
        );

        TicketResponse ticket = proxyTicket.createTicket(userId, ticketRequest);

        payment.setTicketId(ticket.id());
        payment.setBookingReference(ticket.bookingReference());
        paymentRepository.save(payment);

        System.out.println("Payment succeeded--------------------------------------------------");
        sendNotificationBestEffort(userId, payment.getAmount().toString());

        return new PaymentResponse(
                true,
                "Payment successful. Ticket booked successfully.",
                payment.getPaymentReference(),
                ticket.id(),
                ticket.bookingReference()
        );
    }

    private void simulatePaymentDelay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Payment simulation interrupted.");
        }
    }

    private boolean simulatePaymentResult(String paymentMethodId) {
        // MVP rule:
        // Any method id containing "fail" will fail.
        // Everything else succeeds.
        return paymentMethodId == null || !paymentMethodId.toLowerCase().contains("fail");
    }

    private void sendNotificationBestEffort(Long userId, String bookingReference) {
        try {
            System.out.println("Attempting to send notification...");
            notificationClient.sendNotification(
                    new NotificationRequest(
                            userId,
                            "Booking confirmed",
                            "Your booking was confirmed. Paid: " + bookingReference + "€",
                            "GENERAL",
                            "TICKET",
                            null

                    )
            );
            System.out.println("Notification sent successfully.");
        } catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }
    }
}