package com.bestRuralEvents.ContentService.proxy;

import com.bestRuralEvents.ContentService.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "UserService")
public interface UserClient {

    @GetMapping("/internal/users/{userId}")
    UserResponse getUserById(@PathVariable Long userId);
}