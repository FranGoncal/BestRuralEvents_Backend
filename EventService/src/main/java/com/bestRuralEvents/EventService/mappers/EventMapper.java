package com.bestRuralEvents.EventService.mappers;

import com.bestRuralEvents.EventService.DTO.EventRequest;
import com.bestRuralEvents.EventService.DTO.EventResponse;
import com.bestRuralEvents.EventService.models.*;

import java.util.Currency;

public class EventMapper {

    private EventMapper() {
    }

    public static Event toEntity(EventRequest request, Long organizerId) {
        Event event = new Event();

        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setLocation(request.location());
        event.setOrganizerId(organizerId);
        event.setPrice(request.price());
        event.setCurrency(Currency.getInstance("EUR"));
        event.setStatus(EventStatus.APPROVED);

        event.setCategory(
                request.category() != null
                        ? request.category()
                        : EventCategory.OTHER
        );

        event.setTicketMode(
                request.ticketMode() != null
                        ? request.ticketMode()
                        : TicketMode.EVENT_PASS
        );

        event.setDates(new EventDate(
                request.startDate(),
                request.endDate()
        ));

        if (request.images() != null) {
            event.setImages(request.images());
        }

        return event;
    }

    public static void updateEntity(Event event, EventRequest request) {
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setLocation(request.location());
        event.setPrice(request.price());

        event.setCategory(
                request.category() != null
                        ? request.category()
                        : EventCategory.OTHER
        );

        event.setTicketMode(
                request.ticketMode() != null
                        ? request.ticketMode()
                        : TicketMode.EVENT_PASS
        );

        event.setDates(new EventDate(
                request.startDate(),
                request.endDate()
        ));

        if (request.images() != null) {
            event.setImages(request.images());
        }
    }

    public static EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getOrganizerId(),
                event.getStatus(),
                event.getCategory(),
                event.getPrice(),
                event.getCurrency() != null ? event.getCurrency().getCurrencyCode() : "EUR",
                event.getLocation(),
                event.getDates() != null ? event.getDates().getStartDate() : null,
                event.getDates() != null ? event.getDates().getEndDate() : null,
                event.getImages(),
                event.getTicketMode(),
                event.getAverageRating(),
                event.getTotalReviews(),
                event.getCreatedAt(),
                event.getUpdatedAt(),
                event.getPublishedAt()
        );
    }
}