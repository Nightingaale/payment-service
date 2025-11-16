package org.nightingaale.paymentservice.mapper.outbox;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutboxEventFactoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "aggregateType", constant = "PaymentTransaction")
    @Mapping(target = "aggregateId", expression = "java(transactionEntity.getPaymentTransactionId().toString())")
    @Mapping(target = "type", expression = "java(OutboxType.NEW)")
    @Mapping(target = "payload", expression = "java(\"{\\\"transactionId\\\":\\\"\" + transactionEntity.getPaymentTransactionId() + \"\\\",\\\"userId\\\":\\\"\" + transactionEntity.getUserId() + \"\\\",\\\"amount\\\":\\\"\" + transactionEntity.getAmount() + \"\\\",\\\"currency\\\":\\\"\" + transactionEntity.getCurrency() + \"\\\"}\")")
    @Mapping(target = "createdAt", expression = "java(java.time.Instant.now())")
    @Mapping(target = "processed", constant = "false")
    OutboxEventEntity fromPaymentTransaction(PaymentTransactionEntity transactionEntity);
}
