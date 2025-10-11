package org.nightingaale.paymentservice.model.enums;

import lombok.Getter;

@Getter
public enum PaymentMethodProvider {
    VISA,
    MASTERCARD;

    public static PaymentMethodProvider getPaymentMethodProvider(String paymentMethod) {
        for (PaymentMethodProvider paymentMethodProvider : PaymentMethodProvider.values()) {
            if (paymentMethodProvider.toString().equalsIgnoreCase(paymentMethod)) {
                return paymentMethodProvider;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + paymentMethod);
    }
}
