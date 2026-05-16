package com.bestRuralEvents.ContentService.controller;

import com.bestRuralEvents.ContentService.dto.FaqAdminListResponse;
import com.bestRuralEvents.ContentService.dto.FaqListResponse;
import com.bestRuralEvents.ContentService.models.Faq;
import com.bestRuralEvents.ContentService.service.FaqService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/faq")
public class FaqController {

    private final FaqService faqService;

    public FaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    // Existing public endpoint - do not change
    @GetMapping
    public FaqListResponse getFaqs() {
        return new FaqListResponse(
                true,
                "FAQ loaded successfully.",
                faqService.getActiveFaqs()
        );
    }

    // Admin endpoints

    @GetMapping("/admin")
    public FaqAdminListResponse getAllFaqsForAdmin() {
        return new FaqAdminListResponse(
                true,
                "FAQ loaded successfully.",
                faqService.getAllFaqs()
        );
    }

    @GetMapping("/admin/{id}")
    public Faq getFaqById(@PathVariable Long id) {
        return faqService.getById(id);
    }

    @PostMapping("/admin")
    public Faq createFaq(@Valid @RequestBody Faq faq) {
        return faqService.create(faq);
    }

    @PutMapping("/admin/{id}")
    public Faq updateFaq(
            @PathVariable Long id,
            @Valid @RequestBody Faq faq
    ) {
        return faqService.update(id, faq);
    }

    @DeleteMapping("/admin/{id}")
    public void deleteFaq(@PathVariable Long id) {
        faqService.delete(id);
    }
}