package com.bestRuralEvents.ContentService.dto;


public record HelpMessageResponse(
        boolean success,
        String message,
        Long id
) {
}