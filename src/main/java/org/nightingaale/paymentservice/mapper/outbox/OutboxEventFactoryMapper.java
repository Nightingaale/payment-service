package org.nightingaale.paymentservice.mapper.outbox;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutboxEventFactoryMapper {
    @Mapping(target = "aggregateType", constant = "PaymentTransaction")
    @Mapping(target = "aggregateId", expression = "java(transactionEntity.getId().toString())")
    @Mapping(target = "type", constant = "PaymentTransactionCreated")
    @Mapping(target = "payload", expression = "java(\"{\\\"transactionId\\\":\\\"\" + transactionEntity.getId() + \"\\\"}\")")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "processed", constant = "false")
    OutboxEventEntity fromPaymentTransaction(PaymentTransactionEntity transactionEntity);
}
