package org.nightingaale.paymentservice.model.dto;

import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

import java.io.Serializable;

public record PaymentLogDto(
        Long logId,
        PaymentTransactionDto paymentTransactionId,
        RefundTransactionDto refundTransaction,
        PaymentMethodDto paymentMethod,
        PaymentTransactionStatus status,
        PaymentMethodProvider provider,
        PaymentMethodType methodType) implements Serializable {
}