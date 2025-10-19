package org.nightingaale.paymentservice.handler.impl;

import lombok.RequiredArgsConstructor;
import org.nightingaale.paymentservice.handler.api.PaymentMethodHandler;
import org.nightingaale.paymentservice.handler.provider.PaymentProviderHandler;
import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.util.CreditCardConverter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SamsungPayHandler implements PaymentMethodHandler {

    private final CreditCardConverter creditCardConverter;
    private final PaymentProviderHandler paymentProviderHandler;

    @Override
    public PaymentMethodType getPaymentMethodType() {
        return PaymentMethodType.SAMSUNG_PAY;
    }

    @Override
    public PaymentMethodDto process(PaymentMethodDto dto) {
        String maskedCardNumber = creditCardConverter.convertToDatabaseColumn(dto.maskedDetails());
        PaymentMethodProvider provider = paymentProviderHandler.getProviderByCardNumber(dto.maskedDetails());

        return new PaymentMethodDto(
                dto.id(),
                dto.createdAt(),
                dto.updatedAt(),
                PaymentMethodType.SAMSUNG_PAY,
                provider,
                maskedCardNumber
        );
    }
}
