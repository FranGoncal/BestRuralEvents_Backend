package com.bestRuralEvents.PaymentService.proxy;

import com.bestRuralEvents.PaymentService.dto.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "NotificationService")
public interface ProxyNotification {

    @PostMapping("/notifications")
    void sendNotification(NotificationRequest request);
}