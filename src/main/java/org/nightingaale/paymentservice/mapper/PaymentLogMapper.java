package org.nightingaale.paymentservice.mapper;

import org.mapstruct.*;
import org.nightingaale.paymentservice.model.entity.PaymentLogEntity;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentLogMapper {
    @Mapping(target = "logId", ignore = true)
    @Mapping(target = "refundTransaction", ignore = true)
    @Mapping(target = "paymentTransaction", source = "transactionEntity")
    @Mapping(target = "status", expression = "java(transactionEntity.getPaymentStatus())")
    @Mapping(target = "provider", expression = "java(transactionEntity.getProvider())")
    @Mapping(target = "methodType", expression = "java(transactionEntity.getType())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    PaymentLogEntity toLog(PaymentTransactionEntity transactionEntity);
}