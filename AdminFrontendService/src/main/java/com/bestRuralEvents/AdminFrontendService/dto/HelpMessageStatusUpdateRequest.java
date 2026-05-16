package com.bestRuralEvents.AdminFrontendService.dto;

public class HelpMessageStatusUpdateRequest {

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