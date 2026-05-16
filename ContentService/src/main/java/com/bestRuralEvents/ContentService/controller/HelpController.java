package com.bestRuralEvents.ContentService.controller;

import com.bestRuralEvents.ContentService.dto.HelpMessageAdminResponse;
import com.bestRuralEvents.ContentService.dto.HelpMessageRequest;
import com.bestRuralEvents.ContentService.dto.HelpMessageResponse;
import com.bestRuralEvents.ContentService.dto.HelpMessageStatusUpdateRequest;
import com.bestRuralEvents.ContentService.models.HelpMessage;
import com.bestRuralEvents.ContentService.service.HelpMessageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/content/help")
public class HelpController {

    private final HelpMessageService helpMessageService;

    public HelpController(HelpMessageService helpMessageService) {
        this.helpMessageService = helpMessageService;
    }

    @PostMapping
    public HelpMessageResponse submitHelpMessage(
            @Valid @RequestBody HelpMessageRequest request
    ) {
        HelpMessage saved = helpMessageService.create(request);

        return new HelpMessageResponse(
                true,
                "Help message submitted successfully.",
                saved.getId()
        );
    }

    @GetMapping
    public List<HelpMessageAdminResponse> getAllHelpMessages() {
        return helpMessageService.getAll();
    }

    @GetMapping("/{id}")
    public HelpMessageAdminResponse getHelpMessageById(@PathVariable Long id) {
        return helpMessageService.getById(id);
    }


    @PutMapping("/{id}/status")
    public HelpMessageAdminResponse updateStatus(
            @PathVariable Long id,
            @RequestBody HelpMessageStatusUpdateRequest request
    ) {
        return helpMessageService.updateStatus(id, request.getStatus());
    }

}