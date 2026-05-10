package com.bestRuralEvents.ContentService.repository;

import com.bestRuralEvents.ContentService.models.HelpMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpMessageRepository extends JpaRepository<HelpMessage, Long> {
    List<HelpMessage> findAllByOrderByCreatedAtDesc();
}