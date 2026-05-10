package com.bestRuralEvents.PaymentService.proxy;


import com.bestRuralEvents.PaymentService.dto.CreateTicketRequest;
import com.bestRuralEvents.PaymentService.dto.TicketResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "TicketService")
public interface ProxyTicket {

    @PostMapping("/tickets")
    TicketResponse createTicket(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody CreateTicketRequest request
    );
}