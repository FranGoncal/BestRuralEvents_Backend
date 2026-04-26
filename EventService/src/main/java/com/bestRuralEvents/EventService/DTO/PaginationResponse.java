package com.bestRuralEvents.EventService.DTO;

public record PaginationResponse(
        int page,
        int pageSize,
        long total,
        boolean hasMore
) {
}