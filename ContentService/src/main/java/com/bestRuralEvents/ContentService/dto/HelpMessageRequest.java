package com.bestRuralEvents.ContentService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HelpMessageRequest(

        @NotBlank(message = "User ID is required.")
        String userId,

        @NotBlank(message = "Subject is required.")
        @Size(min = 3, max = 150, message = "Subject must be between 3 and 150 characters.")
        String subject,

        @NotBlank(message = "Message is required.")
        @Size(min = 10, max = 2000, message = "Message must be between 10 and 2000 characters.")
        String message
) {
}