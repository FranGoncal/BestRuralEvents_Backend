package com.bestRuralEvents.ContentService.controller;

import com.bestRuralEvents.ContentService.dto.FaqListResponse;
import com.bestRuralEvents.ContentService.service.FaqService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/faq")
public class FaqController {

    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public FaqListResponse getFaqs() {
        return new FaqListResponse(
                true,
                "FAQ loaded successfully.",
                faqService.getActiveFaqs()
        );
    }
}