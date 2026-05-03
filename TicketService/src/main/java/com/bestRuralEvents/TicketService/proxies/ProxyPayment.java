package com.bestRuralEvents.TicketService.proxies;

import com.bestRuralEvents.TicketService.dto.PaymentRequest;
import com.bestRuralEvents.TicketService.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "PAYMENTS")
public interface  ProxyPayment {
    @PostMapping("/payments")
    PaymentResponse createPayment(@RequestBody PaymentRequest request);
}
