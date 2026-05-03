package com.bestRuralEvents.ReviewService.clients;

import com.bestRuralEvents.ReviewService.dto.UpdateEventRatingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "EventService")
public interface EventClient {

    @GetMapping("/events/{eventId}")
    Object getEventById(@PathVariable Long eventId);

    @PutMapping("/events/{eventId}/rating")
    void updateEventRating(
            @PathVariable Long eventId,
            @RequestBody UpdateEventRatingRequest request
    );
}