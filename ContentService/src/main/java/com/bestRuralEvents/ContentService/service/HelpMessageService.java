package com.bestRuralEvents.ContentService.service;

import com.bestRuralEvents.ContentService.dto.HelpMessageAdminResponse;
import com.bestRuralEvents.ContentService.dto.HelpMessageRequest;
import com.bestRuralEvents.ContentService.dto.UserResponse;
import com.bestRuralEvents.ContentService.models.HelpMessage;
import com.bestRuralEvents.ContentService.models.HelpMessageStatus;
import com.bestRuralEvents.ContentService.proxy.UserClient;
import com.bestRuralEvents.ContentService.repository.HelpMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelpMessageService {

    @Autowired
    private HelpMessageRepository helpMessageRepository;

    @Autowired
    private UserClient userClient;

    public HelpMessage create(HelpMessageRequest request) {
        Long userId = Long.valueOf(request.userId());

        UserResponse user = userClient.getUserById(userId);

        HelpMessage helpMessage = new HelpMessage(
                request.userId(),
                user.email(),
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

    public HelpMessageAdminResponse updateStatus(Long id, String status) {
        HelpMessage message = helpMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Help message not found with id: " + id));

        message.setStatus(HelpMessageStatus.valueOf(status));

        HelpMessage saved = helpMessageRepository.save(message);

        return toAdminResponse(saved);
    }

    private HelpMessageAdminResponse toAdminResponse(HelpMessage message) {
        return new HelpMessageAdminResponse(
                message.getId(),
                message.getUserId(),
                message.getEmail(),
                message.getSubject(),
                message.getMessage(),
                message.getStatus(),
                message.getCreatedAt()
        );
    }
}