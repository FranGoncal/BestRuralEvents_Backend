package com.bestRuralEvents.AuthService.proxy;

import com.bestRuralEvents.AuthService.DTO.CreateUserProfileRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "USERSERVICE")
public interface UserProxy {
    @PostMapping("/internal/users")
    void createUserProfile(CreateUserProfileRequest request);
}