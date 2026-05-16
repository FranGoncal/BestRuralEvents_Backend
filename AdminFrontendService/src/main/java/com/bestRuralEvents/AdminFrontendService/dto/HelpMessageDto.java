package com.bestRuralEvents.AdminFrontendService.dto;

import java.time.LocalDateTime;

public class HelpMessageDto {

    private Long id;
    private Long userId;
    private String email;
    private String subject;
    private String message;
    private String status;
    private LocalDateTime createdAt;

    public HelpMessageDto() {
    }

    public HelpMessageDto(
            Long id,
            Long userId,
            String email,
            String subject,
            String message,
            String status,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}