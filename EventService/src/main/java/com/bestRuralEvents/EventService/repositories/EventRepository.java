package com.bestRuralEvents.EventService.repositories;

import com.bestRuralEvents.EventService.models.Event;
import com.bestRuralEvents.EventService.models.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findByOrganizerIdOrderByCreatedAtDesc(Long organizerId);

    List<Event> findTop10ByStatusOrderByPublishedAtDesc(EventStatus status);

    Optional<Event> findTopByOrderByUpdatedAtDesc();
}