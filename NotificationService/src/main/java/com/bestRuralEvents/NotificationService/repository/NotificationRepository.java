package com.bestRuralEvents.NotificationService.repository;

import com.bestRuralEvents.NotificationService.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientUserIdAndDeletedFalseOrderByCreatedAtDesc(Long userId);

    boolean existsByRecipientUserIdAndReadFalseAndDeletedFalse(Long userId);

    List<Notification> findByRecipientUserIdAndDeletedFalse(Long userId);

    List<Notification> findByRecipientUserIdOrderByCreatedAtDesc(Long userId);
}