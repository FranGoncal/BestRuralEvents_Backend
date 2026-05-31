package com.bestRuralEvents.NotificationService.controller;

import com.bestRuralEvents.NotificationService.dto.DeviceTokenDebugResponse;
import com.bestRuralEvents.NotificationService.dto.NotificationDebugResponse;
import com.bestRuralEvents.NotificationService.repository.DeviceTokenRepository;
import com.bestRuralEvents.NotificationService.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private DeviceTokenRepository deviceTokenRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/device-tokens")
    public List<DeviceTokenDebugResponse> getAllDeviceTokens() {
        return deviceTokenRepository.findAll()
                .stream()
                .map(DeviceTokenDebugResponse::fromEntity)
                .toList();
    }

    @GetMapping("/device-tokens/user/{userId}")
    public List<DeviceTokenDebugResponse> getDeviceTokensByUser(
            @PathVariable Long userId
    ) {
        return deviceTokenRepository.findByUserId(userId)
                .stream()
                .map(DeviceTokenDebugResponse::fromEntity)
                .toList();
    }

    @GetMapping("/notifications")
    public List<NotificationDebugResponse> getAllNotifications() {
        return notificationRepository.findAll()
                .stream()
                .map(NotificationDebugResponse::fromEntity)
                .toList();
    }

    @GetMapping("/notifications/user/{userId}")
    public List<NotificationDebugResponse> getNotificationsByUser(
            @PathVariable Long userId
    ) {
        return notificationRepository.findByRecipientUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(NotificationDebugResponse::fromEntity)
                .toList();
    }
}