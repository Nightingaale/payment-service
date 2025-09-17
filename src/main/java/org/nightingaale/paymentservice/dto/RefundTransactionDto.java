package org.nightingaale.paymentservice.dto;

import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

public record RefundTransactionDto(Long id, Long transactionId, Long refundedAmount, PaymentTransactionStatus paymentStatus) {
}
