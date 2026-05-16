package com.bestRuralEvents.AdminFrontendService.security;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class AdminTokenProvider {

    public String bearerToken(HttpSession session) {
        Object token = session.getAttribute("JWT");

        if (token == null) {
            throw new IllegalStateException("Admin JWT not found in session");
        }

        return "Bearer " + token;
    }
}