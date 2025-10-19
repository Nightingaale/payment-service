package org.nightingaale.paymentservice.handler.provider;

import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.springframework.stereotype.Component;

@Component
public class PaymentProviderHandler {

    public PaymentMethodProvider getProviderByCardNumber(String cardNumber) {

        if (cardNumber == null || cardNumber.isBlank()) {
            return PaymentMethodProvider.VISA;
        }

        cardNumber = cardNumber.replaceAll("\\s+", "");

        if (cardNumber.startsWith("4")) {
            return PaymentMethodProvider.VISA;
        } else if (cardNumber.startsWith("5")) {
            return PaymentMethodProvider.MASTERCARD;
        }

        return PaymentMethodProvider.VISA;
    }
}
