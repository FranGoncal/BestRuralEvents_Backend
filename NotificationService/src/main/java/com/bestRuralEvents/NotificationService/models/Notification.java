package com.bestRuralEvents.NotificationService.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long recipientUserId;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 1000)
    private String text;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean read = false;

    private boolean deleted = false;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String relatedEntityType;

    private Long relatedEntityId;

}