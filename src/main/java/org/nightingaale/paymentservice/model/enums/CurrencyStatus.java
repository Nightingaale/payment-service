package org.nightingaale.paymentservice.model.enums;

import lombok.Getter;

@Getter
public enum CurrencyStatus {
    EUR,
    USD,
    ZLT,
    YAN;

    public static CurrencyStatus fromString(String status) {
        for (CurrencyStatus cs : CurrencyStatus.values()) {
            if (cs.name().equalsIgnoreCase(status)) {
                return cs;
            }
        } throw new RuntimeException("Unknown currency status: " + status);
    }
}
