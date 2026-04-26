package com.bestRuralEvents.EventService.controllers;

import com.bestRuralEvents.EventService.DTO.*;
import com.bestRuralEvents.EventService.services.EventService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/main-page")
    public MainPageResponse getMainPageEvents() {
        return eventService.getMainPageEvents();
    }

    @GetMapping("/main-page/meta")
    public MainPageMetaResponse getMainPageMeta() {
        return eventService.getMainPageMeta();
    }

    @GetMapping("/search")
    public SearchEventsResponse searchEvents(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) String activityType,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return eventService.searchEvents(
                query,
                minPrice,
                maxPrice,
                minRating,
                activityType,
                startDate,
                endDate,
                page,
                pageSize
        );
    }

    @GetMapping("/{eventId}")
    public EventResponse getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @GetMapping("/user/{userId}")
    public EventListResponse getUserCreatedEvents(@PathVariable Long userId) {
        return eventService.getOrganizerEvents(userId);
    }

    @PostMapping
    public CreateEventApiResponse createEvent(
            @Valid @RequestBody EventRequest request,
            @RequestHeader("X-User-Id") Long organizerId
    ) {
        EventResponse event = eventService.createEvent(request, organizerId);

        return new CreateEventApiResponse(
                true,
                "Event created successfully.",
                event
        );
    }

    @PutMapping("/{eventId}")
    public UpdateEventApiResponse updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequest request,
            @RequestHeader("X-User-Id") Long organizerId
    ) {
        EventResponse event = eventService.updateEvent(eventId, request, organizerId);

        return new UpdateEventApiResponse(
                true,
                "Event updated successfully.",
                event
        );
    }
}