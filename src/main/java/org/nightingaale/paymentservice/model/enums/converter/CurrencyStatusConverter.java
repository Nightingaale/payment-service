package org.nightingaale.paymentservice.model.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.nightingaale.paymentservice.model.enums.CurrencyStatus;

@Converter(autoApply = true)
public class CurrencyStatusConverter implements AttributeConverter<CurrencyStatus, String> {

    @Override
    public String convertToDatabaseColumn(CurrencyStatus attribute) {
        return attribute == null ? null : attribute.name();
    }

    @Override
    public CurrencyStatus convertToEntityAttribute(String dbData) {
        return dbData == null ? null : CurrencyStatus.valueOf(dbData);
    }
}
