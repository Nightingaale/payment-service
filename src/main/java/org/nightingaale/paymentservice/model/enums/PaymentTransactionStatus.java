package org.nightingaale.paymentservice.model.enums;

import lombok.Getter;

@Getter
public enum PaymentTransactionStatus {
    APPROVED,
    REJECTED,
    PROCESSING;

    public static PaymentTransactionStatus fromString(String status) {
        for (PaymentTransactionStatus value : PaymentTransactionStatus.values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid payment status: " + status);
    }
}
