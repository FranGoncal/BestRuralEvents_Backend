package com.bestRuralEvents.ContentService.dto;

import com.bestRuralEvents.ContentService.models.Faq;

import java.util.List;

public class FaqAdminListResponse {

    private boolean success;
    private String message;
    private List<Faq> faqs;

    public FaqAdminListResponse() {
    }

    public FaqAdminListResponse(boolean success, String message, List<Faq> faqs) {
        this.success = success;
        this.message = message;
        this.faqs = faqs;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Faq> getFaqs() {
        return faqs;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setFaqs(List<Faq> faqs) {
        this.faqs = faqs;
    }
}