package com.bestRuralEvents.TicketService.controllers;

import com.bestRuralEvents.TicketService.dto.*;
import com.bestRuralEvents.TicketService.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

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
    public EventTicketsManagementResponse getEventTickets(
            @RequestHeader("X-User-Id") Long organizerId,
            @PathVariable Long eventId
    ) {
        return ticketService.getEventTicketsForManagement(organizerId, eventId);
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


    @PostMapping("/{ticketId}/refund")
    public TicketResponse refundTicketAsOrganizer(
            @RequestHeader("X-User-Id") Long organizerId,
            @PathVariable Long ticketId
    ) {
        return ticketService.refundTicketAsOrganizer(organizerId, ticketId);
    }
}