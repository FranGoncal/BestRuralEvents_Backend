package com.bestRuralEvents.AdminFrontendService.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FaqDto {

    private Long id;

    @NotBlank(message = "Question is required")
    private String question;

    @NotBlank(message = "Answer is required")
    private String answer;

    private boolean active = true;

    @NotNull(message = "Display order is required")
    private Integer displayOrder = 0;

    public FaqDto() {
    }

    public FaqDto(Long id, String question, String answer, boolean active, Integer displayOrder) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.active = active;
        this.displayOrder = displayOrder;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isActive() {
        return active;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}