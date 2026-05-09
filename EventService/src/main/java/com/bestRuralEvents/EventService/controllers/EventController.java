package com.bestRuralEvents.EventService.controllers;

import com.bestRuralEvents.EventService.DTO.*;
import com.bestRuralEvents.EventService.services.EventService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponse> createEvent(
            @RequestParam String title,
            @RequestParam String location,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam BigDecimal price,
            @RequestParam(required = false) String description,

            @RequestParam(required = false) List<MultipartFile> images,

            @RequestHeader("X-User-Id") Long organizerId
    ) {
        EventResponse response = eventService.createEvent(
                title,
                location,
                startDate,
                endDate,
                price,
                description,
                images,
                organizerId
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(
            value = "/{eventId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public UpdateEventApiResponse updateEvent(
            @PathVariable Long eventId,
            @RequestParam String title,
            @RequestParam String location,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam BigDecimal price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) List<MultipartFile> images,
            @RequestHeader("X-User-Id") Long organizerId
    ) {
        EventResponse event = eventService.updateEvent(
                eventId,
                title,
                location,
                startDate,
                endDate,
                price,
                description,
                images,
                organizerId
        );

        return new UpdateEventApiResponse(
                true,
                "Event updated successfully.",
                event
        );
    }

    @PutMapping("/{eventId}/rating")
    public void updateEventRating(
            @PathVariable Long eventId,
            @RequestBody UpdateEventRatingRequest request
    ) {
        eventService.updateEventRating(eventId, request);
    }
}