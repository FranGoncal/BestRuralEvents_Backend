package com.bestRuralEvents.ContentService.service;

import com.bestRuralEvents.ContentService.dto.HelpMessageAdminResponse;
import com.bestRuralEvents.ContentService.dto.HelpMessageRequest;
import com.bestRuralEvents.ContentService.models.HelpMessage;
import com.bestRuralEvents.ContentService.repository.HelpMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpMessageService {

    private final HelpMessageRepository helpMessageRepository;

    public HelpMessageService(HelpMessageRepository helpMessageRepository) {
        this.helpMessageRepository = helpMessageRepository;
    }

    public HelpMessage create(HelpMessageRequest request) {
        HelpMessage helpMessage = new HelpMessage(
                request.userId(),
                request.subject(),
                request.message()
        );

        return helpMessageRepository.save(helpMessage);
    }

    public List<HelpMessageAdminResponse> getAll() {
        return helpMessageRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toAdminResponse)
                .toList();
    }

    public HelpMessageAdminResponse getById(Long id) {
        HelpMessage message = helpMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Help message not found."));

        return toAdminResponse(message);
    }

    private HelpMessageAdminResponse toAdminResponse(HelpMessage message) {
        return new HelpMessageAdminResponse(
                message.getId(),
                message.getUserId(),
                message.getSubject(),
                message.getMessage(),
                message.getStatus(),
                message.getCreatedAt()
        );
    }
}