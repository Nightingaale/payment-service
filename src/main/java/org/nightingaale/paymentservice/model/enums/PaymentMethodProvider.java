package org.nightingaale.paymentservice.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethodProvider {
    VISA,
    MASTERCARD;

    public static PaymentMethodProvider getPaymentMethodProvider(String paymentMethod) {
        for (PaymentMethodProvider paymentMethodProvider : PaymentMethodProvider.values()) {
            if (paymentMethodProvider.toString().equalsIgnoreCase(paymentMethod)) {
                return paymentMethodProvider;
            }
        }
        throw new IllegalArgumentException("Unknown payment provider: " + paymentMethod);
    }
}
