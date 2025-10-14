package org.nightingaale.paymentservice.model.enums;

import lombok.Getter;

@Getter
public enum RefundTransactionStatus {
    COMPLETED,
    FAILED;

    public static RefundTransactionStatus fromString(String status) {
        for (RefundTransactionStatus value : RefundTransactionStatus.values()) {
            if (value.name().equalsIgnoreCase(status)) {
                return value;
            }
        }
        throw new RuntimeException("Unknown refund transaction status: " + status);
    }
}
