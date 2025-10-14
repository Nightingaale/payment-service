package org.nightingaale.paymentservice.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.nightingaale.paymentservice.model.enums.RefundTransactionStatus;

@Converter(autoApply = true)
public class RefundTransactionStatusConverter implements AttributeConverter<RefundTransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(RefundTransactionStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public RefundTransactionStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : RefundTransactionStatus.valueOf(dbData);
    }
}
