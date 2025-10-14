package org.nightingaale.paymentservice.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;

@Converter(autoApply = true)
public class PaymentMethodProviderConverter implements AttributeConverter<PaymentMethodProvider, String> {

    @Override
    public String convertToDatabaseColumn(PaymentMethodProvider attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public PaymentMethodProvider convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PaymentMethodProvider.valueOf(dbData);
    }
}
