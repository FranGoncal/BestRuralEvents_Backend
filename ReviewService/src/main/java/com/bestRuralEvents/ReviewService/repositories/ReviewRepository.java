package com.bestRuralEvents.ReviewService.repositories;

import com.bestRuralEvents.ReviewService.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByEventIdAndUserId(Long eventId, Long userId);

    List<Review> findByEventIdOrderByCreatedAtDesc(Long eventId);

    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);

    @Query("""
        select avg(r.rating)
        from Review r
        where r.eventId = :eventId
    """)
    Double getAverageRating(Long eventId);

    @Query("""
        select count(r)
        from Review r
        where r.eventId = :eventId
    """)
    Long getTotalReviews(Long eventId);

    Optional<Review> findByEventIdAndUserId(Long eventId, Long userId);
}