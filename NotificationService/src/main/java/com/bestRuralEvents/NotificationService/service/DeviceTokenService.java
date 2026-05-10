package com.bestRuralEvents.NotificationService.service;

import com.bestRuralEvents.NotificationService.dto.DeviceTokenRequest;
import com.bestRuralEvents.NotificationService.models.DeviceToken;
import com.bestRuralEvents.NotificationService.repository.DeviceTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeviceTokenService {

    private final DeviceTokenRepository deviceTokenRepository;

    public DeviceTokenService(DeviceTokenRepository deviceTokenRepository) {
        this.deviceTokenRepository = deviceTokenRepository;
    }

    public void registerToken(Long userId, DeviceTokenRequest request) {
        DeviceToken deviceToken = deviceTokenRepository.findByToken(request.token())
                .orElseGet(DeviceToken::new);

        deviceToken.setUserId(userId);
        deviceToken.setToken(request.token());
        deviceToken.setPlatform(request.platform());
        deviceToken.setActive(true);
        deviceToken.setLastUsedAt(LocalDateTime.now());

        deviceTokenRepository.save(deviceToken);
    }
}