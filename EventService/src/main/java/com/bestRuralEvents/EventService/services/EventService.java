package com.bestRuralEvents.EventService.services;

import com.bestRuralEvents.EventService.DTO.*;
import com.bestRuralEvents.EventService.mappers.EventMapper;
import com.bestRuralEvents.EventService.models.*;
import com.bestRuralEvents.EventService.repositories.EventRepository;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import static com.bestRuralEvents.EventService.mappers.EventMapper.toResponse;


@Service
public class EventService {
    private static final Logger log = LoggerFactory.getLogger(EventService.class);

    private final EventRepository eventRepository;
    private final ImageStorageService imageStorageService;

    public EventService(
            EventRepository eventRepository,
            ImageStorageService imageStorageService
    ) {
        this.eventRepository = eventRepository;
        this.imageStorageService = imageStorageService;
    }

    public EventResponse createEvent(
            String title,
            String location,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal price,
            String description,
            Boolean refundable,
            Integer refundDeadlineDays,
            String refundPolicy,
            TicketMode ticketMode,
            Integer capacity,
            List<LocalDate> dailyCapacityDates,
            List<Integer> dailyCapacityValues,
            List<MultipartFile> images,
            Long organizerId
    ) {
        validateDates(startDate, endDate);
        validateRefundPolicy(refundable, refundDeadlineDays, refundPolicy);

        TicketMode resolvedTicketMode = ticketMode != null ? ticketMode : TicketMode.EVENT_PASS;

        Event event = new Event();

        event.setTitle(title.trim());
        event.setLocation(location.trim());
        event.setPrice(price);
        event.setDescription(description);
        event.setOrganizerId(organizerId);

        event.setCategory(EventCategory.FOOD);
        event.setStatus(EventStatus.APPROVED);
        event.setTicketMode(resolvedTicketMode);

        event.setRefundable(Boolean.TRUE.equals(refundable));
        event.setRefundDeadlineDays(Boolean.TRUE.equals(refundable) ? refundDeadlineDays : null);
        event.setRefundPolicy(Boolean.TRUE.equals(refundable) ? refundPolicy : null);

        EventDate dates = new EventDate();
        dates.setStartDate(startDate);
        dates.setEndDate(endDate);
        event.setDates(dates);

        applyCapacity(
                event,
                resolvedTicketMode,
                capacity,
                dailyCapacityDates,
                dailyCapacityValues,
                startDate,
                endDate
        );

        saveImages(event, images);

        Event saved = eventRepository.save(event);

        return toResponse(saved);
    }


    public EventResponse updateEvent(
            Long eventId,
            String title,
            String location,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal price,
            String description,
            List<MultipartFile> images,
            Long organizerId
    ) {
        validateDates(startDate, endDate);

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getOrganizerId().equals(organizerId)) {
            throw new RuntimeException("You are not allowed to update this event");
        }


        event.setTitle(title.trim());
        event.setLocation(location.trim());
        event.setPrice(price);
        event.setDescription(description);

        EventDate dates = new EventDate();
        dates.setStartDate(startDate);
        dates.setEndDate(endDate);
        event.setDates(dates);

        if (images != null && !images.isEmpty()) {
            event.getImages().clear();
            saveImages(event, images);
        }

        Event savedEvent = eventRepository.save(event);

        return toResponse(savedEvent);
    }

    public EventResponse getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return toResponse(event);
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

    @Transactional
    public void updateEventRating(Long eventId, UpdateEventRatingRequest request) {

        System.out.println("Received rating update for event " + eventId);
        System.out.println("New average: " + request.averageRating());
        System.out.println("New total reviews: " + request.totalReviews());

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setAverageRating(request.averageRating());
        event.setTotalReviews(request.totalReviews());

        eventRepository.save(event);

        System.out.println("Saved successfully.");
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

    private void validateCapacity(EventRequest request) {
        if (request.capacity() == null || request.capacity() < 1) {
            throw new RuntimeException("Capacity must be at least 1");
        }

        if (request.ticketMode() == TicketMode.EVENT_PASS) {
            return;
        }

        if (request.ticketMode() == TicketMode.PER_DAY) {
            if (request.dailyCapacities() == null || request.dailyCapacities().isEmpty()) {
                throw new RuntimeException("Daily capacities are required for PER_DAY events");
            }

            for (EventDayCapacityRequest dayCapacity : request.dailyCapacities()) {
                if (dayCapacity.date() == null) {
                    throw new RuntimeException("Daily capacity date is required");
                }

                if (dayCapacity.capacity() == null || dayCapacity.capacity() < 1) {
                    throw new RuntimeException("Daily capacity must be at least 1");
                }

                if (dayCapacity.date().isBefore(request.startDate()) ||
                        dayCapacity.date().isAfter(request.endDate())) {
                    throw new RuntimeException("Daily capacity date must be inside event date range");
                }
            }
        }
    }

    private void validateRefundPolicy(
            Boolean refundable,
            Integer refundDeadlineDays,
            String refundPolicy
    ) {
        if (!Boolean.TRUE.equals(refundable)) {
            return;
        }

        if (refundDeadlineDays == null || refundDeadlineDays < 0) {
            throw new RuntimeException("Refund deadline days must be zero or greater for refundable events");
        }

        if (refundPolicy == null || refundPolicy.trim().isEmpty()) {
            throw new RuntimeException("Refund policy is required for refundable events");
        }
    }

    private void applyCapacity(
            Event event,
            TicketMode ticketMode,
            Integer capacity,
            List<LocalDate> dailyCapacityDates,
            List<Integer> dailyCapacityValues,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (ticketMode == TicketMode.EVENT_PASS) {
            if (capacity == null || capacity < 1) {
                throw new RuntimeException("Capacity must be at least 1 for event pass tickets");
            }

            event.setCapacity(capacity);
            event.getDailyCapacities().clear();
            return;
        }

        if (ticketMode == TicketMode.PER_DAY) {
            if (dailyCapacityDates == null || dailyCapacityValues == null) {
                throw new RuntimeException("Daily capacities are required for per-day tickets");
            }

            if (dailyCapacityDates.size() != dailyCapacityValues.size()) {
                throw new RuntimeException("Daily capacity dates and values must have the same size");
            }

            event.getDailyCapacities().clear();

            int totalCapacity = 0;

            for (int i = 0; i < dailyCapacityDates.size(); i++) {
                LocalDate date = dailyCapacityDates.get(i);
                Integer dayCapacity = dailyCapacityValues.get(i);

                if (date == null) {
                    throw new RuntimeException("Daily capacity date is required");
                }

                if (dayCapacity == null || dayCapacity < 1) {
                    throw new RuntimeException("Daily capacity must be at least 1");
                }

                if (date.isBefore(startDate) || date.isAfter(endDate)) {
                    throw new RuntimeException("Daily capacity date must be inside event date range");
                }

                event.getDailyCapacities().add(new EventDayCapacity(date, dayCapacity));
                totalCapacity += dayCapacity;
            }

            event.setCapacity(totalCapacity);
        }
    }

    private void saveImages(Event event, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return;
        }

        for (MultipartFile image : images) {
            if (image != null && !image.isEmpty()) {
                String imageUrl = imageStorageService.saveEventImage(image);
                event.getImages().add(imageUrl);
            }
        }
    }

}