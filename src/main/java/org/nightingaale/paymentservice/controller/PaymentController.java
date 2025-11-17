package org.nightingaale.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.nightingaale.paymentservice.event.CreatePaymentTransactionRequest;
import org.nightingaale.paymentservice.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/transaction")
    public ResponseEntity<?> createPaymentRequest(@RequestBody CreatePaymentTransactionRequest request, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        request.setUserId(userId.toString());
        paymentService.createPaymentTransaction(request);
        return ResponseEntity.ok("Transaction has been successfully created");
    }

    @GetMapping("/history")
    public ResponseEntity<List<?>> getPaymentTransactionHistory(@AuthenticationPrincipal Jwt jwt) {
        List<?> transactions = paymentService.getPaymentTransactionsByUserId(jwt.getSubject());
        return ResponseEntity.ok(transactions);
    }
}
