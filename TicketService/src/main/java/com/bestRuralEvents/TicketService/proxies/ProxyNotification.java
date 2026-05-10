package com.bestRuralEvents.TicketService.proxies;

import com.bestRuralEvents.TicketService.dto.NotificationRequest;
import com.bestRuralEvents.TicketService.dto.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "NOTIFICATIONSERVICE")
public interface ProxyNotification {

    @PostMapping("/internal/notifications")
    NotificationResponse createNotification(NotificationRequest request);
}