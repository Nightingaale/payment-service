package org.nightingaale.paymentservice.handler.impl;

import lombok.RequiredArgsConstructor;
import org.nightingaale.paymentservice.handler.api.PaymentMethodHandler;
import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.util.CreditCardConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayPalHandler implements PaymentMethodHandler {

    private final CreditCardConverter creditCardConverter;

    @Override
    public PaymentMethodType getPaymentMethodType() {
        return PaymentMethodType.CREDIT_CARD;
    }

    @Override
    public PaymentMethodDto process(PaymentMethodDto dto) {
        String maskedCardNumber = creditCardConverter.convertToDatabaseColumn(dto.maskedDetails());

        return new PaymentMethodDto(
                dto.id(),
                dto.createdAt(),
                dto.updatedAt(),
                PaymentMethodType.CREDIT_CARD,
                null,
                maskedCardNumber
        );
    }
}
