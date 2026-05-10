package com.bestRuralEvents.ContentService.service;

import com.bestRuralEvents.ContentService.dto.FaqResponse;
import com.bestRuralEvents.ContentService.repository.FaqRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaqService {

    private final FaqRepository faqRepository;

    public FaqService(FaqRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    public List<FaqResponse> getActiveFaqs() {
        return faqRepository.findByActiveTrueOrderByDisplayOrderAsc()
                .stream()
                .map(faq -> new FaqResponse(
                        faq.getQuestion(),
                        faq.getAnswer()
                ))
                .toList();
    }
}