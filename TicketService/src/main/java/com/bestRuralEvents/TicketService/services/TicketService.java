package com.bestRuralEvents.TicketService.services;

import com.bestRuralEvents.TicketService.dto.*;
import com.bestRuralEvents.TicketService.models.Ticket;
import com.bestRuralEvents.TicketService.models.TicketMode;
import com.bestRuralEvents.TicketService.models.TicketStatus;
import com.bestRuralEvents.TicketService.models.TicketType;
import com.bestRuralEvents.TicketService.proxies.ProxyEvent;
import com.bestRuralEvents.TicketService.proxies.ProxyPayment;
import com.bestRuralEvents.TicketService.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ProxyEvent proxyEvent;
    private final ProxyPayment proxyPayment;

    public TicketService(
            TicketRepository ticketRepository,
            ProxyEvent proxyEvent,
            ProxyPayment proxyPayment
    ) {
        this.ticketRepository = ticketRepository;
        this.proxyEvent = proxyEvent;
        this.proxyPayment = proxyPayment;
    }

    public TicketResponse buyTicket(Long userId, CreateTicketRequest request) {
        EventResponse event = proxyEvent.getEventById(request.eventId());

        validateTicketRequest(event, request);

        /*
        PaymentResponse payment = proxyPayment.createPayment(
                new PaymentRequest(
                        userId,
                        event.id(),
                        event.price(),
                        event.currency()
                )
        );

        if (!payment.status().equals("SUCCESS")) {
            throw new RuntimeException("Payment failed");
        }*/

        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setEventId(event.id());
        ticket.setTicketMode(event.ticketMode());
        ticket.setSelectedDays(request.selectedDays() == null ? List.of() : request.selectedDays());
        ticket.setStatus(TicketStatus.ACTIVE);
        ticket.setValidationToken(UUID.randomUUID().toString());
        ticket.setPricePaid(event.price());
        ticket.setCurrency(Currency.getInstance(event.currency()));

        Ticket saved = ticketRepository.save(ticket);

        return toResponse(saved);
    }


    private void validateTicketRequest(EventResponse event, CreateTicketRequest request) {
        if (event == null) {
            throw new RuntimeException("Event not found");
        }

        List<LocalDate> selectedDays =
                request.selectedDays() == null ? List.of() : request.selectedDays();

        if (event.ticketMode() == TicketMode.EVENT_PASS) {
            if (!selectedDays.isEmpty()) {
                throw new RuntimeException("EVENT_PASS tickets should not have selected days");
            }
        }

        if (event.ticketMode() == TicketMode.PER_DAY) {
            if (selectedDays.isEmpty()) {
                throw new RuntimeException("PER_DAY tickets require selected days");
            }

            for (LocalDate day : selectedDays) {
                if (day.isBefore(event.startDate()) || day.isAfter(event.endDate())) {
                    throw new RuntimeException("Selected day must be inside event date range");
                }
            }
        }
    }

    private TicketResponse toResponse(Ticket ticket) {
        return new TicketResponse(
                ticket.getId(),
                ticket.getUserId(),
                ticket.getEventId(),
                ticket.getTicketMode(),
                ticket.getSelectedDays(),
                ticket.getStatus(),
                ticket.getValidationToken(),
                ticket.getPricePaid(),
                ticket.getCurrency(),
                ticket.getCreatedAt()
        );
    }

    public List<TicketResponse> getUserTickets(Long userId) {
        return ticketRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public boolean hasActiveTicket(Long userId, Long eventId) {
        return ticketRepository.existsByUserIdAndEventIdAndStatus(
                userId,
                eventId,
                TicketStatus.ACTIVE
        );
    }

    public List<TicketResponse> getEventTickets(Long organizerId, Long eventId) {
        EventResponse event = proxyEvent.getEventById(eventId);

        return ticketRepository.findByEventIdOrderByCreatedAtDesc(eventId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TicketResponse validateTicket(Long organizerId, ValidateTicketRequest request) {
        Ticket ticket = ticketRepository.findByValidationToken(request.validationToken())
                .orElseThrow(() -> new RuntimeException("Invalid ticket token"));

        EventResponse event = proxyEvent.getEventById(ticket.getEventId());

        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            throw new RuntimeException("Ticket is not active");
        }

        ticket.setStatus(TicketStatus.USED);

        Ticket saved = ticketRepository.save(ticket);

        return toResponse(saved);
    }

    public TicketResponse cancelTicket(Long userId, Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to cancel this ticket");
        }

        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            throw new RuntimeException("Only active tickets can be cancelled");
        }

        EventResponse event = proxyEvent.getEventById(ticket.getEventId());

        if (!Boolean.TRUE.equals(event.refundable())) {
            System.out.println(event.refundable());
            throw new RuntimeException("This event is not refundable");
        }

        LocalDate refundBaseDate;

        if (ticket.getTicketMode() == TicketMode.EVENT_PASS) {
            refundBaseDate = event.startDate();
        } else {
            refundBaseDate = ticket.getSelectedDays()
                    .stream()
                    .min(LocalDate::compareTo)
                    .orElse(event.startDate());
        }

        int deadlineDays = event.refundDeadlineDays() == null
                ? 0
                : event.refundDeadlineDays();

        LocalDate refundDeadline = refundBaseDate.minusDays(deadlineDays);

        if (LocalDate.now().isAfter(refundDeadline)) {
            throw new RuntimeException("Refund deadline has passed");
        }

        ticket.setStatus(TicketStatus.CANCELLED);

        Ticket saved = ticketRepository.save(ticket);

        return toResponse(saved);
    }

}