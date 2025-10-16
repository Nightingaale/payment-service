package org.nightingaale.paymentservice.model.dto;

import jakarta.validation.constraints.NotNull;
import org.nightingaale.paymentservice.model.enums.CurrencyStatus;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentTransactionDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @NotNull Long paymentTransactionId,
        @NotNull PaymentTransactionStatus paymentStatus,
        @NotNull BigDecimal amount,
        @NotNull CurrencyStatus currency) implements Serializable {
}