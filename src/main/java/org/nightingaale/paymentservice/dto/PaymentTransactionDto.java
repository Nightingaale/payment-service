package org.nightingaale.paymentservice.dto;

import org.nightingaale.paymentservice.enums.PaymentTransactionStatus;

public record PaymentTransactionDto(Long id, Long transactionId, PaymentTransactionStatus paymentStatus, String errorMessage, String currency) {

}

