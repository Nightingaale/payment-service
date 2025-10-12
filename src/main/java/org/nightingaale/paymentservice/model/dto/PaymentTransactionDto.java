package org.nightingaale.paymentservice.model.dto;

import jakarta.validation.constraints.NotNull;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PaymentTransactionDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @NotNull Long paymentTransactionId,
        @NotNull PaymentTransactionStatus paymentStatus,
        @NotNull Long amount,
        @NotNull String currency) implements Serializable {
}