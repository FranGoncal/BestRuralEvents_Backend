package com.bestRuralEvents.EventService.mappers;

import com.bestRuralEvents.EventService.DTO.EventDayCapacityResponse;
import com.bestRuralEvents.EventService.DTO.EventRequest;
import com.bestRuralEvents.EventService.DTO.EventResponse;
import com.bestRuralEvents.EventService.models.*;

import java.util.Currency;
import java.util.List;

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

        event.setRefundable(
                request.refundable() != null ? request.refundable() : false
        );

        event.setRefundDeadlineDays(request.refundDeadlineDays());
        event.setRefundPolicy(request.refundPolicy());

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

        event.setCapacity(request.capacity());

        if (request.dailyCapacities() != null) {
            event.setDailyCapacities(
                    request.dailyCapacities()
                            .stream()
                            .map(dc -> new EventDayCapacity(dc.date(), dc.capacity()))
                            .toList()
            );
        }

        return event;
    }

    public static void updateEntity(Event event, EventRequest request) {
        event.setTitle(request.title());
        event.setDescription(request.description());
        event.setLocation(request.location());
        event.setPrice(request.price());


        /*event.setRefundable(
                request.refundable() != null ? request.refundable() : false
        );

        event.setRefundDeadlineDays(request.refundDeadlineDays());
        event.setRefundPolicy(request.refundPolicy());*/

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
        event.setCapacity(request.capacity());

        event.getDailyCapacities().clear();

        if (request.dailyCapacities() != null) {
            event.getDailyCapacities().addAll(
                    request.dailyCapacities()
                            .stream()
                            .map(dc -> new EventDayCapacity(dc.date(), dc.capacity()))
                            .toList()
            );
        }
    }

    public static EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getOrganizerId(),
                event.getCapacity(),
                event.getDailyCapacities() == null
                        ? List.of()
                        : event.getDailyCapacities()
                          .stream()
                          .map(dc -> new EventDayCapacityResponse(dc.getDate(), dc.getCapacity()))
                          .toList(),
                event.getStatus(),
                event.getCategory(),
                event.getPrice(),
                event.getCurrency(),
                event.getLocation(),
                event.getDates().getStartDate(),
                event.getDates().getEndDate(),
                event.getImages(),
                event.getTicketMode(),
                event.getRefundable(),
                event.getRefundDeadlineDays(),
                event.getRefundPolicy(),
                event.getAverageRating() == null ? 0.0 : event.getAverageRating(),
                event.getTotalReviews() == null ? 0 : event.getTotalReviews()
        );
    }
}