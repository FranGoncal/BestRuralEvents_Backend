package com.bestRuralEvents.EventService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 3000)
    private String description;

    @Column(nullable = false)
    private Long organizerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventCategory category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private String location;

    @Embedded
    private EventDate dates;

    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketMode ticketMode;

    private Double averageRating;

    @Column(nullable = false)
    private Integer totalReviews = 0;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime publishedAt;

    @PrePersist
    public void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;

        if (this.status == null) {
            this.status = EventStatus.APPROVED;
        }

        if (this.currency == null) {
            this.currency = Currency.getInstance("EUR");
        }

        if (this.totalReviews == null) {
            this.totalReviews = 0;
        }

        if (this.ticketMode == null) {
            this.ticketMode = TicketMode.EVENT_PASS;
        }

        if (this.status == EventStatus.APPROVED && this.publishedAt == null) {
            this.publishedAt = now;
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();

        if (this.status == EventStatus.APPROVED && this.publishedAt == null) {
            this.publishedAt = LocalDateTime.now();
        }
    }
}