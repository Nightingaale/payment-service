package org.nightingaale.paymentservice.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Component;

@Converter(autoApply = false)
@Component
public class CreditCardConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null) {
            return null;
        }

        String digits = attribute.replaceAll("[^0-9]", "");
        if (digits.length() <= 4) {
            return digits;
        }

        int hidden = Math.min(12, digits.length() - 4);
        String masked = "*".repeat(hidden) + digits.substring(digits.length() - 4);
        return masked;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
