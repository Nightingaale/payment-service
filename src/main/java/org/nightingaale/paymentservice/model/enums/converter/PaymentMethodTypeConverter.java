package org.nightingaale.paymentservice.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;

@Converter(autoApply = true)
public class PaymentMethodTypeConverter implements AttributeConverter<PaymentMethodType, String> {

    @Override
    public String convertToDatabaseColumn(PaymentMethodType attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public PaymentMethodType convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PaymentMethodType.valueOf(dbData);
    }
}
