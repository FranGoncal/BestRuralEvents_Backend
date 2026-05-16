package com.bestRuralEvents.AdminFrontendService.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @GetMapping({"/", "/admin", "/admin/dashboard"})
    public String dashboard(HttpSession session, Model model) {
        model.addAttribute("adminEmail", session.getAttribute("ADMIN_EMAIL"));
        return "admin/dashboard";
    }
}