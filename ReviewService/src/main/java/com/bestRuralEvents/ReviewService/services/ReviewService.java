package com.bestRuralEvents.ReviewService.services;

import com.bestRuralEvents.ReviewService.dto.*;
import com.bestRuralEvents.ReviewService.proxy.EventProxy;
import com.bestRuralEvents.ReviewService.models.Review;
import com.bestRuralEvents.ReviewService.proxy.UserProxy;
import com.bestRuralEvents.ReviewService.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private EventProxy eventClient;

    @Autowired
    private UserProxy userClient;

    @Transactional
    public ReviewResponse createReview(CreateReviewRequest request, Long userId) {

        eventClient.getEventById(request.eventId());

        Review review = reviewRepository
                .findByEventIdAndUserId(request.eventId(), userId)
                .orElseGet(() -> {
                    Review newReview = new Review();
                    newReview.setEventId(request.eventId());
                    newReview.setUserId(userId);
                    return newReview;
                });

        review.setRating(request.rating());
        review.setComment(cleanComment(request.comment()));

        Review saved = reviewRepository.save(review);

        updateEventRatingAggregate(request.eventId());

        return toResponse(saved);
    }

    public List<ReviewResponse> getReviewsForEvent(Long eventId) {
        return reviewRepository
                .findByEventIdOrderByCreatedAtDesc(eventId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<ReviewResponse> getReviewsByUser(Long userId) {
        return reviewRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RatingSummaryResponse getRatingSummary(Long eventId) {
        Double average = reviewRepository.getAverageRating(eventId);
        Long total = reviewRepository.getTotalReviews(eventId);

        return new RatingSummaryResponse(
                eventId,
                average == null ? 0.0 : Math.round(average * 10.0) / 10.0,
                total
        );
    }

    @Transactional
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUserId().equals(userId)) {
            throw new IllegalStateException("You can only delete your own review");
        }

        Long eventId = review.getEventId();

        reviewRepository.delete(review);

        updateEventRatingAggregate(eventId);
    }

    @Transactional
    public ReviewResponse updateReview(
            Long reviewId,
            CreateReviewRequest request,
            Long userId
    ) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        if (!review.getUserId().equals(userId)) {
            throw new IllegalStateException("You can only update your own review");
        }

        if (!review.getEventId().equals(request.eventId())) {
            throw new IllegalStateException("You cannot move a review to another event");
        }

        review.setRating(request.rating());
        review.setComment(cleanComment(request.comment()));

        Review updated = reviewRepository.save(review);

        updateEventRatingAggregate(review.getEventId());

        return toResponse(updated);
    }

    private void updateEventRatingAggregate(Long eventId) {
        RatingSummaryResponse summary = getRatingSummary(eventId);

        eventClient.updateEventRating(
                eventId,
                new UpdateEventRatingRequest(
                        summary.averageRating(),
                        summary.totalReviews().intValue()
                )
        );
    }

    private String cleanComment(String comment) {
        return comment == null ? null : comment.trim();
    }

    private ReviewResponse toResponse(Review review) {
        EventResponse event = eventClient.getEventById(review.getEventId());
        UserResponse user = userClient.getUserById(review.getUserId());

        return new ReviewResponse(
                review.getId(),
                review.getEventId(),
                review.getUserId(),
                user.name(),
                event.title(),
                event.startDate(),
                event.endDate(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }


}