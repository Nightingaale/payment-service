package org.nightingaale.paymentservice.model.dto;

import jakarta.validation.constraints.NotNull;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PaymentMethodDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @NotNull PaymentMethodType type,
        @NotNull PaymentMethodProvider provider,
        @NotNull String maskedDetails) implements Serializable {
}