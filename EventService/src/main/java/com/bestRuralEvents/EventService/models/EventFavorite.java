package com.bestRuralEvents.EventService.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "event_favorites",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "event_id"})
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public EventFavorite(Long userId, Event event) {
        this.userId = userId;
        this.event = event;
        this.createdAt = LocalDateTime.now();
    }
}