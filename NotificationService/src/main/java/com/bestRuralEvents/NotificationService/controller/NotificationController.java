package com.bestRuralEvents.NotificationService.controller;

import com.bestRuralEvents.NotificationService.dto.MessageResponse;
import com.bestRuralEvents.NotificationService.dto.NotificationResponse;
import com.bestRuralEvents.NotificationService.dto.NotificationStatusResponse;
import com.bestRuralEvents.NotificationService.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getNotifications(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return notificationService.getUserNotificationsAndMarkAsRead(userId);
    }

    @GetMapping("/status")
    public NotificationStatusResponse getNotificationStatus(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return new NotificationStatusResponse(
                notificationService.hasUnreadNotifications(userId)
        );
    }

    @DeleteMapping("/{id}")
    public MessageResponse deleteNotification(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id
    ) {
        notificationService.deleteNotification(userId, id);
        return new MessageResponse("Notification deleted successfully.");
    }

    @DeleteMapping
    public MessageResponse deleteAllNotifications(
            @RequestHeader("X-User-Id") Long userId
    ) {
        notificationService.deleteAllNotifications(userId);
        return new MessageResponse("All notifications deleted successfully.");
    }
}