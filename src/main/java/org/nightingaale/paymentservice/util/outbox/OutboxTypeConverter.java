package org.nightingaale.paymentservice.util.outbox;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.nightingaale.paymentservice.model.enums.outbox.OutboxType;


@Converter(autoApply = true)
public class OutboxTypeConverter implements AttributeConverter<OutboxType, String> {

    @Override
    public String convertToDatabaseColumn(OutboxType status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public OutboxType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return OutboxType.fromValue(dbData);
    }
}
