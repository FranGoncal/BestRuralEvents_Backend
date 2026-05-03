package com.bestRuralEvents.TicketService.repositories;

import com.bestRuralEvents.TicketService.models.Ticket;
import com.bestRuralEvents.TicketService.models.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Ticket> findByEventIdOrderByCreatedAtDesc(Long eventId);

    Optional<Ticket> findByValidationToken(String validationToken);

    boolean existsByUserIdAndEventIdAndStatus(
            Long userId,
            Long eventId,
            TicketStatus status
    );
}