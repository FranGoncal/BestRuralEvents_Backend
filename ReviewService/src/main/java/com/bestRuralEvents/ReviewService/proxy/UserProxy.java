package com.bestRuralEvents.ReviewService.proxy;
import com.bestRuralEvents.ReviewService.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "UserService")
public interface UserProxy {

    @GetMapping("/internal/users/{userId}")
    UserResponse getUserById(@PathVariable Long userId);
}