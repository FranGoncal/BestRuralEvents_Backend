package com.bestRuralEvents.TicketService.proxies;

import com.bestRuralEvents.TicketService.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "USERSERVICE")
public interface ProxyUser {
    @GetMapping("/internal/users/{userId}")
    UserResponse getUserById(@PathVariable Long userId);
}