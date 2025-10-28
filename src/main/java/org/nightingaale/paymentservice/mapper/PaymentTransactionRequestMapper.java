package org.nightingaale.paymentservice.mapper;

import org.mapstruct.*;
import org.nightingaale.paymentservice.event.CreatePaymentTransactionRequest;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentTransactionRequestMapper {
    PaymentTransactionEntity toEntity(CreatePaymentTransactionRequest paymentTransactionRequest);

    CreatePaymentTransactionRequest toDto(PaymentTransactionEntity paymentTransactionEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PaymentTransactionEntity partialUpdate(CreatePaymentTransactionRequest paymentTransactionRequest, @MappingTarget PaymentTransactionEntity paymentTransactionEntity);
}