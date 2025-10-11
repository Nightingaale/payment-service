package org.nightingaale.paymentservice.model.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodType {
    GOOGLE_PAY,
    APPLE_PAY,
    PAYPAL,
    HUAWEI_PAY,
    SAMSUNG_PAY,
    CREDIT_CARD;

    public static PaymentMethodType fromString(String value) {
        for (PaymentMethodType paymentMethodType : PaymentMethodType.values()) {
            if (paymentMethodType.toString().equalsIgnoreCase(value)) {
                return paymentMethodType;
            }
        }
        throw new IllegalArgumentException("Invalid payment method type: " + value);
    }
}