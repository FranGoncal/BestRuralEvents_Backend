package com.bestRuralEvents.ReviewService.controller;

import com.bestRuralEvents.ReviewService.dto.CreateReviewRequest;
import com.bestRuralEvents.ReviewService.dto.RatingSummaryResponse;
import com.bestRuralEvents.ReviewService.dto.ReviewResponse;
import com.bestRuralEvents.ReviewService.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ReviewResponse createReview(
            @Valid @RequestBody CreateReviewRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        return reviewService.createReview(request, userId);
    }

    @GetMapping("/event/{eventId}")
    public List<ReviewResponse> getReviewsForEvent(@PathVariable Long eventId) {
        return reviewService.getReviewsForEvent(eventId);
    }

    @GetMapping("/me")
    public List<ReviewResponse> getMyReviews(
            @RequestHeader("X-User-Id") Long userId
    ) {
        return reviewService.getReviewsByUser(userId);
    }

    @GetMapping("/event/{eventId}/summary")
    public RatingSummaryResponse getRatingSummary(@PathVariable Long eventId) {
        return reviewService.getRatingSummary(eventId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(
            @PathVariable Long reviewId,
            @RequestHeader("X-User-Id") Long userId
    ) {
        reviewService.deleteReview(reviewId, userId);
    }

    @PutMapping("/{reviewId}")
    public ReviewResponse updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody CreateReviewRequest request,
            @RequestHeader("X-User-Id") Long userId
    ) {
        return reviewService.updateReview(reviewId, request, userId);
    }
}