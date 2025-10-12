package org.nightingaale.paymentservice.mapper;

import org.mapstruct.*;
import org.nightingaale.paymentservice.model.dto.PaymentTransactionDto;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentTransactionMapper {
    PaymentTransactionEntity toEntity(PaymentTransactionDto paymentTransactionDto);

    PaymentTransactionDto toDto(PaymentTransactionEntity paymentTransactionEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PaymentTransactionEntity partialUpdate(PaymentTransactionDto paymentTransactionDto, @MappingTarget PaymentTransactionEntity paymentTransactionEntity);
}