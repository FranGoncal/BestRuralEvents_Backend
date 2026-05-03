package com.bestRuralEvents.EventService.repositories;

import com.bestRuralEvents.EventService.models.Event;
import com.bestRuralEvents.EventService.models.EventFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventFavoriteRepository extends JpaRepository<EventFavorite, Long> {

    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    Optional<EventFavorite> findByUserIdAndEventId(Long userId, Long eventId);

    List<EventFavorite> findByUserIdOrderByCreatedAtDesc(Long userId);

    void deleteByUserIdAndEventId(Long userId, Long eventId);
}