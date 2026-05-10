package com.bestRuralEvents.NotificationService.service;

import com.bestRuralEvents.NotificationService.dto.CreateNotificationRequest;
import com.bestRuralEvents.NotificationService.dto.NotificationResponse;
import com.bestRuralEvents.NotificationService.models.DeviceToken;
import com.bestRuralEvents.NotificationService.models.Notification;
import com.bestRuralEvents.NotificationService.repository.DeviceTokenRepository;
import com.bestRuralEvents.NotificationService.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final DeviceTokenRepository deviceTokenRepository;
    private final FirebasePushService firebasePushService;

    public NotificationService(
            NotificationRepository notificationRepository,
            DeviceTokenRepository deviceTokenRepository,
            FirebasePushService firebasePushService
    ) {
        this.notificationRepository = notificationRepository;
        this.deviceTokenRepository = deviceTokenRepository;
        this.firebasePushService = firebasePushService;
    }

    @Transactional
    public Notification createAndSend(CreateNotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipientUserId(request.recipientUserId());
        notification.setTitle(request.title());
        notification.setText(request.text());
        notification.setType(request.type());
        notification.setRelatedEntityType(request.relatedEntityType());
        notification.setRelatedEntityId(request.relatedEntityId());

        Notification saved = notificationRepository.save(notification);

        List<DeviceToken> tokens = deviceTokenRepository.findByUserIdAndActiveTrue(request.recipientUserId());
        firebasePushService.sendPushToUserDevices(saved, tokens);

        return saved;
    }

    @Transactional
    public List<NotificationResponse> getUserNotificationsAndMarkAsRead(Long userId) {
        List<Notification> notifications =
                notificationRepository.findByRecipientUserIdAndDeletedFalseOrderByCreatedAtDesc(userId);

        for (Notification notification : notifications) {
            notification.setRead(true);
        }

        return notifications.stream()
                .map(NotificationResponse::fromEntity)
                .toList();
    }

    public boolean hasUnreadNotifications(Long userId) {
        return notificationRepository.existsByRecipientUserIdAndReadFalseAndDeletedFalse(userId);
    }

    @Transactional
    public void deleteNotification(Long userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found."));

        if (!notification.getRecipientUserId().equals(userId)) {
            throw new RuntimeException("You cannot delete this notification.");
        }

        notification.setDeleted(true);
    }

    @Transactional
    public void deleteAllNotifications(Long userId) {
        List<Notification> notifications =
                notificationRepository.findByRecipientUserIdAndDeletedFalse(userId);

        for (Notification notification : notifications) {
            notification.setDeleted(true);
        }
    }
}