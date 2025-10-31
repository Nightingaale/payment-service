package org.nightingaale.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.nightingaale.paymentservice.event.CreatePaymentTransactionRequest;
import org.nightingaale.paymentservice.model.dto.PaymentTransactionDto;
import org.nightingaale.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/transaction")
    public ResponseEntity<?> createPaymentRequest(@RequestBody CreatePaymentTransactionRequest request) {
        paymentService.createPaymentTransaction(request);
        return ResponseEntity.ok("Transaction has been successfully created");
    }

    @GetMapping("/history")
    public ResponseEntity<PaymentTransactionDto> getPaymentTransactionHistory(@PathVariable Long paymentTransactionId) {
        return paymentService.getPaymentTransaction(paymentTransactionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
