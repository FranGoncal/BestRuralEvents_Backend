package com.bestRuralEvents.NotificationService.controller;

import com.bestRuralEvents.NotificationService.dto.DeviceTokenRequest;
import com.bestRuralEvents.NotificationService.dto.MessageResponse;
import com.bestRuralEvents.NotificationService.service.DeviceTokenService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification/device-token")
public class DeviceTokenController {

    private final DeviceTokenService deviceTokenService;

    public DeviceTokenController(DeviceTokenService deviceTokenService) {
        this.deviceTokenService = deviceTokenService;
    }

    @PostMapping
    public MessageResponse registerDeviceToken(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody DeviceTokenRequest request
    ) {
        deviceTokenService.registerToken(userId, request);
        return new MessageResponse("Device token registered successfully.");
    }
}