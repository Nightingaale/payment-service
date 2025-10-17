package org.nightingaale.paymentservice.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.util.CreditCardJsonSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PaymentMethodDto(
        Long id,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        @NotNull PaymentMethodType type,
        @NotNull PaymentMethodProvider provider,
        @JsonSerialize(using = CreditCardJsonSerializer.class)
        String maskedDetails) implements Serializable {
}