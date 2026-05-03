package com.bestRuralEvents.EventService.services;

import com.bestRuralEvents.EventService.DTO.EventResponse;
import com.bestRuralEvents.EventService.DTO.FavoriteEventsResponse;
import com.bestRuralEvents.EventService.DTO.FavoriteStatusResponse;
import com.bestRuralEvents.EventService.mappers.EventMapper;
import com.bestRuralEvents.EventService.models.Event;
import com.bestRuralEvents.EventService.models.EventFavorite;
import com.bestRuralEvents.EventService.repositories.EventFavoriteRepository;
import com.bestRuralEvents.EventService.repositories.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventFavoriteService {

    private final EventFavoriteRepository favoriteRepository;
    private final EventRepository eventRepository;

    public EventFavoriteService(
            EventFavoriteRepository favoriteRepository,
            EventRepository eventRepository
    ) {
        this.favoriteRepository = favoriteRepository;
        this.eventRepository = eventRepository;
    }

    public FavoriteEventsResponse getFavoriteEvents(Long userId) {
        validateUserId(userId);

        List<EventResponse> events = favoriteRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(EventFavorite::getEvent)
                .map(EventMapper::toResponse)
                .toList();

        return new FavoriteEventsResponse(
                "Favorite events loaded successfully.",
                events
        );
    }

    public FavoriteStatusResponse isFavorite(Long userId, Long eventId) {
        validateUserId(userId);
        validateEventId(eventId);

        boolean isFavorite = favoriteRepository.existsByUserIdAndEventId(userId, eventId);

        return new FavoriteStatusResponse(isFavorite);
    }

    public FavoriteStatusResponse addFavorite(Long userId, Long eventId) {
        validateUserId(userId);
        validateEventId(eventId);

        if (favoriteRepository.existsByUserIdAndEventId(userId, eventId)) {
            return new FavoriteStatusResponse(true);
        }

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        favoriteRepository.save(new EventFavorite(userId, event));

        return new FavoriteStatusResponse(true);
    }

    @Transactional
    public FavoriteStatusResponse removeFavorite(Long userId, Long eventId) {
        validateUserId(userId);
        validateEventId(eventId);

        favoriteRepository.deleteByUserIdAndEventId(userId, eventId);

        return new FavoriteStatusResponse(false);
    }

    private void validateUserId(Long userId) {
        if (userId == null || userId < 1) {
            throw new RuntimeException("User id is required");
        }
    }

    private void validateEventId(Long eventId) {
        if (eventId == null || eventId < 1) {
            throw new RuntimeException("Event id is required");
        }
    }
}