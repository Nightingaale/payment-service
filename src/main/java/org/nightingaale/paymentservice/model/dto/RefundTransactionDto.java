package org.nightingaale.paymentservice.model.dto;

import jakarta.validation.constraints.NotNull;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RefundTransactionDto(
        String id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @NotNull String refundTransactionId,
        @NotNull BigDecimal refundedAmount,
        @NotNull PaymentTransactionStatus paymentStatus,
        @NotNull String errorMessage) implements Serializable {
}