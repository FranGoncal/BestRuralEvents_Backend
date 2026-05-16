package com.bestRuralEvents.AdminFrontendService.controller;

import com.bestRuralEvents.AdminFrontendService.dto.LoginRequest;
import com.bestRuralEvents.AdminFrontendService.dto.LoginResponse;
import com.bestRuralEvents.AdminFrontendService.proxy.AuthClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {

    private final AuthClient authClient;

    public AdminAuthController(AuthClient authClient) {
        this.authClient = authClient;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginRequest request,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        try {
            LoginResponse response = authClient.login(request);

            if (!"ADMIN".equals(response.getRole())) {
                redirectAttributes.addFlashAttribute("error", "Only admins can access this area.");
                return "redirect:/admin/login";
            }

            session.setAttribute("JWT", response.getAccessToken());
            session.setAttribute("ADMIN_EMAIL", response.getEmail());
            session.setAttribute("ADMIN_ID", response.getUserId());

            return "redirect:/admin/dashboard";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Invalid login.");
            return "redirect:/admin/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/login";
    }
}