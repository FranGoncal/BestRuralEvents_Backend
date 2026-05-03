package com.bestRuralEvents.TicketService.controllers;

import com.bestRuralEvents.TicketService.dto.*;
import com.bestRuralEvents.TicketService.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public TicketResponse buyTicket(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CreateTicketRequest request
    ) {
        return ticketService.buyTicket(userId, request);
    }

    @GetMapping("/my")
    public List<TicketResponse> getMyTickets(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return ticketService.getUserTickets(userId);
    }

    @PostMapping("/{ticketId}/cancel")
    public TicketResponse cancelTicket(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long ticketId
    ) {
        return ticketService.cancelTicket(userId, ticketId);
    }

    @GetMapping("/event/{eventId}")
    public List<TicketResponse> getEventTickets(
            @RequestHeader("X-User-Id") Long organizerId,
            @PathVariable Long eventId
    ) {
        return ticketService.getEventTickets(organizerId, eventId);
    }

    @GetMapping("/check-ownership")
    public boolean checkTicketOwnership(
            @RequestParam Long userId,
            @RequestParam Long eventId
    ) {
        return ticketService.hasActiveTicket(userId, eventId);
    }

    @GetMapping("/{ticketId}/valid")
    public TicketValidationResponse validateTicket(
            @PathVariable Long ticketId
    ) {
        return ticketService.validateTicket(ticketId);
    }
}