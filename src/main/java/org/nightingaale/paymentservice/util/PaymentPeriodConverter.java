package org.nightingaale.paymentservice.util;

import org.nightingaale.paymentservice.model.enums.PaymentPeriod;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PaymentPeriodConverter implements Converter<String, PaymentPeriod> {

    @Override
    public PaymentPeriod convert(String source) {
        if (source == null) {
            return null;
        }

        String raw = source.trim();

        if (!raw.equals(raw.toLowerCase())) {
            throw new IllegalArgumentException("PaymentPeriod must be in lowercase (today, yesterday, last_7_days, month)");
        }

        return PaymentPeriod.valueOf(raw.toUpperCase());
    }
}
