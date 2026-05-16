package com.bestRuralEvents.ContentService.service;

import com.bestRuralEvents.ContentService.dto.FaqResponse;
import com.bestRuralEvents.ContentService.models.Faq;
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

    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    public Faq getById(Long id) {
        return faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ not found with id: " + id));
    }

    public Faq create(Faq faq) {
        faq.setId(null);

        if (faq.getDisplayOrder() == null) {
            faq.setDisplayOrder(0);
        }

        return faqRepository.save(faq);
    }

    public Faq update(Long id, Faq updatedFaq) {
        Faq existingFaq = getById(id);

        existingFaq.setQuestion(updatedFaq.getQuestion());
        existingFaq.setAnswer(updatedFaq.getAnswer());
        existingFaq.setCategory(updatedFaq.getCategory());
        existingFaq.setDisplayOrder(
                updatedFaq.getDisplayOrder() != null ? updatedFaq.getDisplayOrder() : 0
        );
        existingFaq.setActive(updatedFaq.isActive());

        return faqRepository.save(existingFaq);
    }

    public void delete(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new RuntimeException("FAQ not found with id: " + id);
        }

        faqRepository.deleteById(id);
    }
}