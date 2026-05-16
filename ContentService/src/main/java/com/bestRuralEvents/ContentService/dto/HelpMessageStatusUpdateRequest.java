package com.bestRuralEvents.ContentService.dto;

import jakarta.validation.constraints.NotBlank;

public class HelpMessageStatusUpdateRequest {

    @NotBlank
    private String status;

    public HelpMessageStatusUpdateRequest() {
    }

    public HelpMessageStatusUpdateRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}