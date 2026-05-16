package com.bestRuralEvents.AdminFrontendService.proxy;

import com.bestRuralEvents.AdminFrontendService.dto.LoginRequest;
import com.bestRuralEvents.AdminFrontendService.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

@FeignClient(value = "AuthService")
public interface AuthClient {

    @PostMapping("/auth/login")
    LoginResponse login(@RequestBody LoginRequest request);
}