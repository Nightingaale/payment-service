package org.nightingaale.paymentservice.model.enums.outbox;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OutboxType {
    NEW("NEW"),
    PUBLISHED("PENDING"),
    CONSUMED("CONSUMED"),
    ERROR("ERROR");

    private String value;

    public static OutboxType fromValue(String value) {
        for (OutboxType status : OutboxType.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
