package org.nightingaale.paymentservice.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

@Converter(autoApply = true)
public class PaymentTransactionStatusConverter implements AttributeConverter<PaymentTransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(PaymentTransactionStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public PaymentTransactionStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : PaymentTransactionStatus.valueOf(dbData);
    }
}
