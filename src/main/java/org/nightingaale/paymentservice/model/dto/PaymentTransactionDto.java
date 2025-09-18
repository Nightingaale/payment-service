package org.nightingaale.paymentservice.model.dto;

import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

public record PaymentTransactionDto(Long id, Long transactionId, PaymentTransactionStatus paymentStatus, String errorMessage, String currency) {

}

