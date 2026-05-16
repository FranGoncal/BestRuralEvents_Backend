package com.bestRuralEvents.AdminFrontendService.controller;

import com.bestRuralEvents.AdminFrontendService.dto.HelpMessageStatusUpdateRequest;
import com.bestRuralEvents.AdminFrontendService.proxy.ContentClient;
import com.bestRuralEvents.AdminFrontendService.security.AdminTokenProvider;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/help-messages")
public class AdminHelpMessageController {

    private final ContentClient contentClient;
    private final AdminTokenProvider tokenProvider;

    public AdminHelpMessageController(
            ContentClient contentClient,
            AdminTokenProvider tokenProvider
    ) {
        this.contentClient = contentClient;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping
    public String list(Model model, HttpSession session) {

        model.addAttribute(
                "messages",
                contentClient.getHelpMessages(
                        tokenProvider.bearerToken(session)
                )
        );

        return "admin/help-messages/list";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            Model model,
            HttpSession session
    ) {

        model.addAttribute(
                "message",
                contentClient.getHelpMessageById(
                        id,
                        tokenProvider.bearerToken(session)
                )
        );

        return "admin/help-messages/detail";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status,
            HttpSession session
    ) {

        contentClient.updateHelpMessageStatus(
                id,
                new HelpMessageStatusUpdateRequest(status),
                tokenProvider.bearerToken(session)
        );

        return "redirect:/admin/help-messages/" + id;
    }
}