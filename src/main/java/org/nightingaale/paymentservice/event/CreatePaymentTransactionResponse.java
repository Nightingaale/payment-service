package org.nightingaale.paymentservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentTransactionResponse {
    private String paymentTransactionId;
    private PaymentTransactionStatus paymentStatus;
    private BigDecimal amount;
    private String currency;
    private String message;
    private LocalDateTime responseAt;
}
