package com.bestRuralEvents.ContentService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HelpMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 150)
    private String subject;

    @Column(nullable = false, length = 2000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HelpMessageStatus status = HelpMessageStatus.OPEN;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Custom constructor for creation
    public HelpMessage(String userId, String subject, String message) {
        this.userId = userId;
        this.subject = subject;
        this.message = message;
        this.status = HelpMessageStatus.OPEN;
        this.createdAt = LocalDateTime.now();
    }
}