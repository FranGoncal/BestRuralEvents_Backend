package com.bestRuralEvents.ContentService.dto;

import java.util.List;

public record FaqListResponse(
        boolean success,
        String message,
        List<FaqResponse> faqs
) {
}