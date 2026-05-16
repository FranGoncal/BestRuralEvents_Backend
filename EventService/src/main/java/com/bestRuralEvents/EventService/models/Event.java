package com.bestRuralEvents.EventService.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean refundable = false;

    private Integer refundDeadlineDays;

    @Column(length = 1000)
    private String refundPolicy;

    @Column(nullable = false)
    private String title;

    @Column(length = 3000)
    private String description;

    @Column(nullable = false)
    private Long organizerId;

    @Column(nullable = false)
    private Integer capacity;

    @ElementCollection
    @CollectionTable(
            name = "event_daily_capacities",
            joinColumns = @JoinColumn(name = "event_id")
    )
    private List<EventDayCapacity> dailyCapacities = new ArrayList<>();

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

    @ElementCollection
    @CollectionTable(
            name = "event_images",
            joinColumns = @JoinColumn(name = "event_id")
    )
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    public Integer getRefundDeadlineDays() {
        return refundDeadlineDays;
    }

    public void setRefundDeadlineDays(Integer refundDeadlineDays) {
        this.refundDeadlineDays = refundDeadlineDays;
    }

    public String getRefundPolicy() {
        return refundPolicy;
    }

    public void setRefundPolicy(String refundPolicy) {
        this.refundPolicy = refundPolicy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<EventDayCapacity> getDailyCapacities() {
        return dailyCapacities;
    }

    public void setDailyCapacities(List<EventDayCapacity> dailyCapacities) {
        this.dailyCapacities = dailyCapacities;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventDate getDates() {
        return dates;
    }

    public void setDates(EventDate dates) {
        this.dates = dates;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public TicketMode getTicketMode() {
        return ticketMode;
    }

    public void setTicketMode(TicketMode ticketMode) {
        this.ticketMode = ticketMode;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Integer getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(Integer totalReviews) {
        this.totalReviews = totalReviews;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }
}