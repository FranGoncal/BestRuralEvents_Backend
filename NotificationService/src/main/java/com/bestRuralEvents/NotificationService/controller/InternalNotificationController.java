package com.bestRuralEvents.NotificationService.controller;

import com.bestRuralEvents.NotificationService.dto.CreateNotificationRequest;
import com.bestRuralEvents.NotificationService.dto.CreateNotificationResponse;
import com.bestRuralEvents.NotificationService.models.Notification;
import com.bestRuralEvents.NotificationService.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/notifications")
public class InternalNotificationController {

    private final NotificationService notificationService;

    public InternalNotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public CreateNotificationResponse createNotification(
            @Valid @RequestBody CreateNotificationRequest request
    ) {
        System.out.println("NOTIFY!!");
        Notification notification = notificationService.createAndSend(request);

        return new CreateNotificationResponse(
                notification.getId(),
                "Notification created successfully."
        );
    }
}