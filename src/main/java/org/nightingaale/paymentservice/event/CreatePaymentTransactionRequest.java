package org.nightingaale.paymentservice.event;

import jakarta.validation.constraints.NotNull;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatePaymentTransactionRequest(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long version,
        @NotNull Long paymentTransactionId,
        @NotNull PaymentTransactionStatus paymentStatus,
        @NotNull BigDecimal amount,
        @NotNull String currency,
        @NotNull PaymentMethodType type,
        @NotNull PaymentMethodProvider provider,
        @NotNull String maskedDetails) implements Serializable {
}
