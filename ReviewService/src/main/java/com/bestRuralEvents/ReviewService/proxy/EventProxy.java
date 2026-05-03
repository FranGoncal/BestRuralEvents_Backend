package com.bestRuralEvents.ReviewService.proxy;

import com.bestRuralEvents.ReviewService.dto.EventResponse;
import com.bestRuralEvents.ReviewService.dto.UpdateEventRatingRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "EventService")
public interface EventProxy {

    @GetMapping("/events/{eventId}")
    EventResponse getEventById(@PathVariable Long eventId);

    @PutMapping("/events/{eventId}/rating")
    void updateEventRating(
            @PathVariable Long eventId,
            @RequestBody UpdateEventRatingRequest request
    );
}