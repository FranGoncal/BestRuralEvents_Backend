package com.bestRuralEvents.AdminFrontendService.controller;

import com.bestRuralEvents.AdminFrontendService.dto.FaqDto;
import com.bestRuralEvents.AdminFrontendService.dto.FaqListResponse;
import com.bestRuralEvents.AdminFrontendService.proxy.ContentClient;
import com.bestRuralEvents.AdminFrontendService.security.AdminTokenProvider;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/faqs")
public class AdminFaqController {

    @Autowired
    private ContentClient contentClient;

    @Autowired
    private AdminTokenProvider tokenProvider;

    @GetMapping
    public String listFaqs(Model model, HttpSession session) {
        FaqListResponse response = contentClient.getFaqs(
                tokenProvider.bearerToken(session)
        );

        model.addAttribute("faqs", response.getFaqs());

        return "admin/faqs/list";
    }

    @GetMapping("/new")
    public String newFaqForm(Model model) {
        model.addAttribute("faq", new FaqDto());
        return "admin/faqs/form";
    }

    @PostMapping
    public String createFaq(@ModelAttribute FaqDto faq, HttpSession session) {
        contentClient.createFaq(faq, tokenProvider.bearerToken(session));
        return "redirect:/admin/faqs";
    }

    @GetMapping("/{id}/edit")
    public String editFaqForm(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("faq", contentClient.getFaqById(id, tokenProvider.bearerToken(session)));
        return "admin/faqs/form";
    }

    @PostMapping("/{id}")
    public String updateFaq(
            @PathVariable Long id,
            @ModelAttribute FaqDto faq,
            HttpSession session
    ) {
        contentClient.updateFaq(id, faq, tokenProvider.bearerToken(session));
        return "redirect:/admin/faqs";
    }

    @PostMapping("/{id}/delete")
    public String deleteFaq(@PathVariable Long id, HttpSession session) {
        contentClient.deleteFaq(id, tokenProvider.bearerToken(session));
        return "redirect:/admin/faqs";
    }
}