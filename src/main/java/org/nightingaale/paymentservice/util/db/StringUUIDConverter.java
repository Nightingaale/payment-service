package org.nightingaale.paymentservice.util.db;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.UUID;

@Converter(autoApply = true)
public class StringUUIDConverter implements AttributeConverter<String, UUID> {

    @Override
    public UUID convertToDatabaseColumn(String attribute) {
        return attribute != null ? UUID.fromString(attribute) : null;
    }

    @Override
    public String convertToEntityAttribute(UUID dbData) {
        return dbData != null ? dbData.toString() : null;
    }
}
