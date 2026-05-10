package com.bestRuralEvents.ContentService.repository;

import com.bestRuralEvents.ContentService.models.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaqRepository extends JpaRepository<Faq, Long> {
    List<Faq> findByActiveTrueOrderByDisplayOrderAsc();
}