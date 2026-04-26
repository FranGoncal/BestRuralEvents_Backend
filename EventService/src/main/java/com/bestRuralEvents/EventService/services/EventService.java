package com.bestRuralEvents.EventService.services;

import com.bestRuralEvents.EventService.DTO.*;
import com.bestRuralEvents.EventService.mappers.EventMapper;
import com.bestRuralEvents.EventService.models.Event;
import com.bestRuralEvents.EventService.models.EventStatus;
import com.bestRuralEvents.EventService.repositories.EventRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class EventService {
    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventResponse createEvent(EventRequest request, Long organizerId) {
        validateDates(request.startDate(), request.endDate());

        Event event = EventMapper.toEntity(request, organizerId);
        Event savedEvent = eventRepository.save(event);

        return EventMapper.toResponse(savedEvent);
    }

    public EventResponse updateEvent(Long eventId, EventRequest request, Long organizerId) {
        validateDates(request.startDate(), request.endDate());

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getOrganizerId().equals(organizerId)) {
            throw new RuntimeException("You are not allowed to update this event");
        }

        EventMapper.updateEntity(event, request);

        Event savedEvent = eventRepository.save(event);

        return EventMapper.toResponse(savedEvent);
    }

    public EventResponse getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return EventMapper.toResponse(event);
    }

    public EventListResponse getOrganizerEvents(Long organizerId) {
        List<EventResponse> events = eventRepository.findByOrganizerIdOrderByCreatedAtDesc(organizerId)
                .stream()
                .map(EventMapper::toResponse)
                .toList();

        return new EventListResponse("User created events loaded successfully.", events);
    }

    public MainPageResponse getMainPageEvents() {
        List<EventResponse> events = eventRepository
                .findTop10ByStatusOrderByPublishedAtDesc(EventStatus.APPROVED)
                .stream()
                .map(EventMapper::toResponse)
                .toList();

        return new MainPageResponse(
                "Main page events loaded successfully.",
                getLastUpdated(),
                events
        );
    }

    public MainPageMetaResponse getMainPageMeta() {
        return new MainPageMetaResponse(getLastUpdated());
    }

    public SearchEventsResponse searchEvents(
            String query,
            Double minPrice,
            Double maxPrice,
            Double minRating,
            String activityType,
            LocalDate startDate,
            LocalDate endDate,
            int page,
            int pageSize
    ) {

        Pageable pageable = PageRequest.of(
                page,
                pageSize,
                Sort.by(Sort.Direction.ASC, "dates.startDate")
        );

        log.info("Searching events with query={}, minPrice={}, maxPrice={}, minRating={}, activityType={}, startDate={}, endDate={}, page={}, pageSize={}",
                query, minPrice, maxPrice, minRating, activityType, startDate, endDate, page, pageSize);

        Specification<Event> specification = buildSearchSpecification(
                query,
                minPrice,
                maxPrice,
                minRating,
                activityType,
                startDate,
                endDate
        );

        Page<Event> result = eventRepository.findAll(specification, pageable);

        List<EventResponse> events = result.getContent()
                .stream()
                .map(EventMapper::toResponse)
                .toList();

        PaginationResponse pagination = new PaginationResponse(
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.hasNext()
        );

        log.info("Search returned {} events out of total {}", result.getNumberOfElements(), result.getTotalElements());

        return new SearchEventsResponse("Search completed.", events, pagination);
    }

    private Specification<Event> buildSearchSpecification(
            String query,
            Double minPrice,
            Double maxPrice,
            Double minRating,
            String activityType,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("status"), EventStatus.APPROVED));

            if (query != null && !query.trim().isEmpty()) {
                String likeQuery = "%" + query.trim().toLowerCase() + "%";

                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        likeQuery
                ));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (activityType != null && !activityType.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(
                        root.get("category").as(String.class),
                        activityType.trim().toUpperCase()
                ));
            }

            if (startDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("dates").get("endDate"),
                        startDate
                ));
            }

            if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("dates").get("startDate"),
                        endDate
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private LocalDateTime getLastUpdated() {
        return eventRepository.findTopByOrderByUpdatedAtDesc()
                .map(Event::getUpdatedAt)
                .orElse(LocalDateTime.now());
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new RuntimeException("Start date and end date are required");
        }

        if (endDate.isBefore(startDate)) {
            throw new RuntimeException("End date cannot be before start date");
        }
    }
}