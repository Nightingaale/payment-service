package org.nightingaale.paymentservice.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CreditCardJsonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if (value == null || value.isBlank()) {
            jsonGenerator.writeString("");
            return;
        }

        String digits = value.replaceAll("[^0-9]", "");
        if (digits.length() <= 4) {
            jsonGenerator.writeString(digits);
            return;
        }

        int hidden = Math.min(12, Math.max(0, digits.length() - 4));
        String masked = "*".repeat(hidden) + digits.substring(digits.length() - 4);

        String formatted = masked.replaceAll("(.{4})", "$1 ").trim();

        jsonGenerator.writeString(formatted);
    }
}
