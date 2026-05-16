package com.bestRuralEvents.PaymentService.proxy;

import com.bestRuralEvents.PaymentService.dto.CreateNotificationResponse;
import com.bestRuralEvents.PaymentService.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATIONSERVICE")
public interface ProxyNotification {

    @PostMapping("/internal/notifications")
    CreateNotificationResponse sendNotification(@RequestBody NotificationRequest request);
}