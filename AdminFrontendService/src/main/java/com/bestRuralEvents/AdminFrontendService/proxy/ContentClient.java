package com.bestRuralEvents.AdminFrontendService.proxy;

import com.bestRuralEvents.AdminFrontendService.dto.FaqDto;
import com.bestRuralEvents.AdminFrontendService.dto.FaqListResponse;
import com.bestRuralEvents.AdminFrontendService.dto.HelpMessageDto;
import com.bestRuralEvents.AdminFrontendService.dto.HelpMessageStatusUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "ContentService")
public interface ContentClient {

    @GetMapping("/content/faq/admin")
    FaqListResponse getFaqs(@RequestHeader("Authorization") String authorization);

    @GetMapping("/content/faq/admin/{id}")
    FaqDto getFaqById(@PathVariable Long id,
                      @RequestHeader("Authorization") String authorization);

    @PostMapping("/content/faq/admin")
    void createFaq(@RequestBody FaqDto faq,
                   @RequestHeader("Authorization") String authorization);

    @PutMapping("/content/faq/admin/{id}")
    void updateFaq(@PathVariable Long id,
                   @RequestBody FaqDto faq,
                   @RequestHeader("Authorization") String authorization);

    @DeleteMapping("/content/faq/admin/{id}")
    void deleteFaq(@PathVariable Long id,
                   @RequestHeader("Authorization") String authorization);

    @GetMapping("/content/help")
    List<HelpMessageDto> getHelpMessages(
            @RequestHeader("Authorization") String authorization
    );

    @GetMapping("/content/help/{id}")
    HelpMessageDto getHelpMessageById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorization
    );

    /**
     * This will only work after you add this endpoint in ContentService.
     */
    @PutMapping("/content/help/{id}/status")
    void updateHelpMessageStatus(
            @PathVariable Long id,
            @RequestBody HelpMessageStatusUpdateRequest request,
            @RequestHeader("Authorization") String token
    );
}