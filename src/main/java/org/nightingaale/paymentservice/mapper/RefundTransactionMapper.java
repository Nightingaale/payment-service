package org.nightingaale.paymentservice.mapper;

import org.mapstruct.*;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.RefundTransactionEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RefundTransactionMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "refundTransactionId", ignore = true)
    @Mapping(target = "refundedAmount", source = "transactionEntity.amount")
    @Mapping(target = "refundStatus", expression = "java(org.nightingaale.paymentservice.model.enums.RefundTransactionStatus.FAILED)")
    @Mapping(target = "currency", source = "transactionEntity.currency")
    @Mapping(target = "errorMessage", source = "errorMessage")
    @Mapping(target = "maskedDetails", expression = "java(transactionEntity.getMaskedDetails())")
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    RefundTransactionEntity toRefund(PaymentTransactionEntity transactionEntity, String errorMessage);
}