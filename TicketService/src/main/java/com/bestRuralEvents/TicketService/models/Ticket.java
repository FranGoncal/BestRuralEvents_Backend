package com.bestRuralEvents.TicketService.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketMode ticketMode;

    @ElementCollection
    @CollectionTable(
            name = "ticket_selected_days",
            joinColumns = @JoinColumn(name = "ticket_id")
    )
    @Column(name = "selected_day")
    private List<LocalDate> selectedDays = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;

    @Column(nullable = false, unique = true)
    private String validationToken;

    @Column(nullable = false)
    private BigDecimal pricePaid;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Ticket() {

    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.status == null) {
            this.status = TicketStatus.ACTIVE;
        }

        if (this.quantity == null) {
            this.quantity = 1;
        }
    }


    public Ticket(Long id, Long userId, Long eventId, Integer quantity, TicketMode ticketMode, List<LocalDate> selectedDays, TicketStatus status, String validationToken, BigDecimal pricePaid, Currency currency, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.eventId = eventId;
        this.quantity = quantity;
        this.ticketMode = ticketMode;
        this.selectedDays = selectedDays;
        this.status = status;
        this.validationToken = validationToken;
        this.pricePaid = pricePaid;
        this.currency = currency;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public TicketMode getTicketMode() {
        return ticketMode;
    }

    public void setTicketMode(TicketMode ticketMode) {
        this.ticketMode = ticketMode;
    }

    public List<LocalDate> getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(List<LocalDate> selectedDays) {
        this.selectedDays = selectedDays;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getValidationToken() {
        return validationToken;
    }

    public void setValidationToken(String validationToken) {
        this.validationToken = validationToken;
    }

    public BigDecimal getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(BigDecimal pricePaid) {
        this.pricePaid = pricePaid;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}