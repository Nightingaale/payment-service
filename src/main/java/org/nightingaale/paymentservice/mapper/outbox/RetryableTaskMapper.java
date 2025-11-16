package org.nightingaale.paymentservice.mapper.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.entity.outbox.RetryableTaskEntity;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskStatus;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;

import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {UUID.class, RetryableTaskStatus.class, Instant.class})
public interface RetryableTaskMapper {

    @Mapping(target = "id", ignore = true, expression = "java(UUID.randomUUID())")
    @Mapping(target = "version", constant = "0L")
    @Mapping(target = "createdAt", expression = "java(Instant.now())")
    @Mapping(target = "updatedAt", expression = "java(Instant.now())")
    @Mapping(source = "outbox", target = "payload", qualifiedByName = "convertObjectToJson")
    @Mapping(target = "retryTime", expression = "java(Instant.now())")
    @Mapping(target = "status", expression = "java(RetryableTaskStatus.IN_PROGRESS)")
    @Mapping(target = "type", expression = "java(RetryableTaskType.SEND_CREATE_DELIVERY_REQUEST)")
    RetryableTaskEntity toRetryableTask(OutboxEventEntity outbox, RetryableTaskType type);

    @Named("convertObjectToJson")
    default String convertObjectToJson(OutboxEventEntity outboxes) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.writeValueAsString(outboxes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting PaymentTransaction to JSON", e);
        }
    }

    @Named("convertJsonToPaymentTransaction")
    default PaymentTransactionEntity convertJsonToTransaction(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(json, PaymentTransactionEntity.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to PaymentTransaction", e);
        }
    }
}
