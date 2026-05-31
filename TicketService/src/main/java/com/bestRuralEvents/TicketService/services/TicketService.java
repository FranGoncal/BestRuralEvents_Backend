package com.bestRuralEvents.TicketService.services;

import com.bestRuralEvents.TicketService.dto.*;
import com.bestRuralEvents.TicketService.models.Ticket;
import com.bestRuralEvents.TicketService.models.TicketMode;
import com.bestRuralEvents.TicketService.models.TicketStatus;
import com.bestRuralEvents.TicketService.models.TicketType;
import com.bestRuralEvents.TicketService.proxies.ProxyEvent;
import com.bestRuralEvents.TicketService.proxies.ProxyNotification;
import com.bestRuralEvents.TicketService.proxies.ProxyPayment;
import com.bestRuralEvents.TicketService.proxies.ProxyUser;
import com.bestRuralEvents.TicketService.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ProxyEvent proxyEvent;

    @Autowired
    private ProxyNotification proxyNotification;

    @Autowired
    private ProxyUser proxyUser;
        

    public EventTicketsManagementResponse getEventTicketsForManagement(Long organizerId, Long eventId) {
        EventResponse event = proxyEvent.getEventById(eventId);

        if (event == null) {
            throw new RuntimeException("Event not found");
        }

        if (!event.organizerId().equals(organizerId)) {
            throw new RuntimeException("You are not allowed to manage tickets for this event");
        }

        List<Ticket> tickets = ticketRepository.findByEventIdOrderByCreatedAtDesc(eventId);

        List<Ticket> activeTickets = tickets.stream()
                .filter(ticket -> ticket.getStatus() != TicketStatus.CANCELLED)
                .toList();

        List<EventTicketDayStatsResponse> dayStats = buildDayStats(event, activeTickets);

        int totalTicketsSold = dayStats.stream()
                .mapToInt(EventTicketDayStatsResponse::ticketsSold)
                .sum();

        Integer totalTicketsAvailable = event.capacity() == null
                ? null
                : dayStats.stream()
                  .map(EventTicketDayStatsResponse::ticketsAvailable)
                  .filter(value -> value != null)
                  .mapToInt(Integer::intValue)
                  .sum();

        List<TicketResponse> responses = tickets.stream()
                .map(this::toResponse)
                .toList();

        return new EventTicketsManagementResponse(
                "Event tickets loaded successfully.",
                event.id(),
                event.title(),
                event.ticketMode(),
                event.capacity(),
                totalTicketsSold,
                totalTicketsAvailable,
                dayStats,
                responses
        );
    }

    public TicketResponse buyTicket(Long userId, CreateTicketRequest request) {
        System.out.println("BUY TICKET REQUEST QUANTITY = " + request.quantity());
        System.out.println("BUY TICKET REQUEST SELECTED DAYS = " + request.selectedDays());
        EventResponse event = proxyEvent.getEventById(request.eventId());

        validateTicketRequest(event, request);

        int quantity = request.quantity() == null ? 1 : request.quantity();

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be at least 1");
        }

        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setEventId(event.id());
        ticket.setQuantity(quantity);
        ticket.setTicketMode(event.ticketMode());
        ticket.setSelectedDays(request.selectedDays() == null ? List.of() : request.selectedDays());
        ticket.setStatus(TicketStatus.ACTIVE);
        ticket.setValidationToken(UUID.randomUUID().toString());
        ticket.setPricePaid(event.price());
        ticket.setCurrency(event.currency());

        Ticket saved = ticketRepository.save(ticket);

        return toResponse(saved);
    }

    private List<EventTicketDayStatsResponse> buildDayStats(EventResponse event, List<Ticket> activeTickets) {
        if (event.startDate() == null || event.endDate() == null) {
            return List.of();
        }

        List<EventTicketDayStatsResponse> result = new ArrayList<>();

        LocalDate current = event.startDate();

        while (!current.isAfter(event.endDate())) {
            LocalDate day = current;

            Integer dayCapacity = getCapacityForDay(event, day);

            int soldForDay = activeTickets.stream()
                    .filter(ticket -> ticketAppliesToDay(ticket, event.ticketMode(), day))
                    .mapToInt(ticket -> ticket.getQuantity() == null ? 1 : ticket.getQuantity())
                    .sum();

            Integer available = dayCapacity == null
                    ? null
                    : Math.max(dayCapacity - soldForDay, 0);

            result.add(new EventTicketDayStatsResponse(
                    day,
                    dayCapacity,
                    soldForDay,
                    available
            ));

            current = current.plusDays(1);
        }

        return result;
    }
    private Integer getCapacityForDay(EventResponse event, LocalDate day) {
        if (event.dailyCapacities() == null || event.dailyCapacities().isEmpty()) {
            return event.capacity();
        }

        return event.dailyCapacities()
                .stream()
                .filter(dailyCapacity -> dailyCapacity.date().equals(day))
                .map(EventDailyCapacityResponse::capacity)
                .findFirst()
                .orElse(event.capacity());
    }

    private boolean ticketAppliesToDay(Ticket ticket, TicketMode ticketMode, LocalDate day) {
        if (ticketMode == TicketMode.EVENT_PASS) {
            return true;
        }

        if (ticketMode == TicketMode.PER_DAY) {
            return ticket.getSelectedDays() != null && ticket.getSelectedDays().contains(day);
        }

        return false;
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
        EventResponse event = proxyEvent.getEventById(ticket.getEventId());
        UserResponse user = proxyUser.getUserById(ticket.getUserId());

        String imageUrl = "";

        if (event.images() != null && !event.images().isEmpty()) {
            imageUrl = event.images().get(0);
        }

        TicketEventResponse ticketEvent = new TicketEventResponse(
                event.id(),
                event.title(),
                event.location(),
                event.startDate(),
                imageUrl,
                event.price(),
                event.averageRating(),
                event.totalReviews(),
                event.description()
        );

        return new TicketResponse(
                ticket.getId(),
                ticketEvent,
                ticket.getPricePaid(),
                event.refundPolicy(),
                ticket.getCreatedAt(),
                canCancel(ticket, event),
                ticket.getStatus() == TicketStatus.ACTIVE || ticket.getStatus() == TicketStatus.USED,
                ticket.getQuantity(),
                user.name(),
                user.email(),
                ticket.getCreatedAt(),
                ticket.getStatus(),
                ticket.getSelectedDays()
        );
    }

    public List<TicketResponse> getUserTickets(Long userId) {
        return ticketRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .filter(ticket -> ticket.getStatus() != TicketStatus.CANCELLED)
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

    public TicketValidationResponse validateTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElse(null);

        if (ticket == null) {
            return new TicketValidationResponse(
                    false,
                    ticketId,
                    null,
                    null,
                    null
            );
        }

        boolean valid = ticket.getStatus() == TicketStatus.ACTIVE;

        return new TicketValidationResponse(
                valid,
                ticket.getId(),
                ticket.getUserId(),
                ticket.getEventId(),
                ticket.getValidationToken()
        );
    }

    public TicketResponse cancelTicket(Long userId, Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        if (!ticket.getUserId().equals(userId)) {
            throw new RuntimeException("You are not allowed to cancel this ticket");
        }

        EventResponse event = proxyEvent.getEventById(ticket.getEventId());

        /*if (!canCancel(ticket, event)) {
            throw new RuntimeException("This ticket can no longer be cancelled");
        }*/

        ticket.setStatus(TicketStatus.CANCELLED);

        Ticket saved = ticketRepository.save(ticket);

        sendNotificationSafely(
                new NotificationRequest(
                        userId,
                        "Ticket cancelled",
                        "The ticket for '" + event.title() + "' was cancelled successfully.",
                        "BOOKING_CANCELLED",
                        "TICKET",
                        saved.getId()
                )
        );

        return toResponse(saved);
    }



    private boolean canCancel(Ticket ticket, EventResponse event) {
        if (ticket.getStatus() != TicketStatus.ACTIVE) {
            return false;
        }

        if (!Boolean.TRUE.equals(event.refundable())) {
            return false;
        }

        if (event.startDate() == null || event.refundDeadlineDays() == null) {
            return false;
        }

        LocalDate limitDate = event.startDate().minusDays(event.refundDeadlineDays());

        return LocalDate.now().isBefore(limitDate);
    }

    public TicketResponse refundTicketAsOrganizer(Long organizerId, Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        EventResponse event = proxyEvent.getEventById(ticket.getEventId());

        if (!event.organizerId().equals(organizerId)) {
            throw new RuntimeException("You are not allowed to refund this ticket");
        }

        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            throw new RuntimeException("Ticket is already cancelled");
        }

        ticket.setStatus(TicketStatus.CANCELLED);

        Ticket saved = ticketRepository.save(ticket);

        sendNotificationSafely(
                new NotificationRequest(
                        ticket.getUserId(),
                        "Ticket refunded",
                        "Your ticket for '" + event.title() + "' was refunded by the organizer.",
                        "BOOKING_CANCELLED",
                        "TICKET",
                        saved.getId()
                )
        );

        return toResponse(saved);
    }

    private void sendNotificationSafely(NotificationRequest request) {
        try {
            proxyNotification.createNotification(request);
        } catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }
    }

}