package com.bestRuralEvents.AdminFrontendService.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String path = request.getRequestURI();

        if (path.equals("/admin/login")
                || path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")) {
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("JWT") == null) {
            response.sendRedirect("/admin/login");
            return false;
        }

        return true;
    }
}