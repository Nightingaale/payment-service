package org.nightingaale.paymentservice.handler.api;

import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;

public interface PaymentMethodHandler {

    PaymentMethodType getPaymentMethodType();

    PaymentMethodDto process(PaymentMethodDto dto);
}
