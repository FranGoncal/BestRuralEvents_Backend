package com.bestRuralEvents.TicketService.proxies;

import com.bestRuralEvents.TicketService.dto.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "EVENTSERVICE")
public interface ProxyEvent {

    @GetMapping("/events/{eventId}")
    EventResponse getEventById(@PathVariable Long eventId);
}