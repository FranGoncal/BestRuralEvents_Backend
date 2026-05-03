package com.bestRuralEvents.EventService.controllers;

import com.bestRuralEvents.EventService.DTO.FavoriteEventsResponse;
import com.bestRuralEvents.EventService.DTO.FavoriteStatusResponse;
import com.bestRuralEvents.EventService.services.EventFavoriteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventFavoriteController {

    private final EventFavoriteService favoriteService;

    public EventFavoriteController(EventFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/favourites")
    public FavoriteEventsResponse getFavoriteEvents(@RequestParam Long userId) {
        return favoriteService.getFavoriteEvents(userId);
    }

    @GetMapping("/{eventId}/favorite")
    public FavoriteStatusResponse isFavorite(
            @PathVariable Long eventId,
            @RequestParam Long userId
    ) {
        return favoriteService.isFavorite(userId, eventId);
    }

    @PostMapping("/{eventId}/favorite")
    public FavoriteStatusResponse addFavorite(
            @PathVariable Long eventId,
            @RequestParam Long userId
    ) {
        return favoriteService.addFavorite(userId, eventId);
    }

    @DeleteMapping("/{eventId}/favorite")
    public FavoriteStatusResponse removeFavorite(
            @PathVariable Long eventId,
            @RequestParam Long userId
    ) {
        return favoriteService.removeFavorite(userId, eventId);
    }
}