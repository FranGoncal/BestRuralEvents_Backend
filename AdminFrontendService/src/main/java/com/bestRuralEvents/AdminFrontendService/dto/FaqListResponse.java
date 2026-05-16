package com.bestRuralEvents.AdminFrontendService.dto;

import java.util.List;

public class FaqListResponse {

    private boolean success;
    private String message;
    private List<FaqDto> faqs;

    public FaqListResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<FaqDto> getFaqs() {
        return faqs;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFaqs(List<FaqDto> faqs) {
        this.faqs = faqs;
    }
}