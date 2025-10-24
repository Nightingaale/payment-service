package org.nightingaale.paymentservice.util.outbox;

import jakarta.persistence.AttributeConverter;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;

public class RetryableTaskTypeConverter implements AttributeConverter<RetryableTaskType, String> {

    @Override
    public String convertToDatabaseColumn(RetryableTaskType status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public RetryableTaskType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return RetryableTaskType.fromValue(dbData);
    }
}
