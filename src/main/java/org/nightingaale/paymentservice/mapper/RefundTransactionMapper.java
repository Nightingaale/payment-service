package org.nightingaale.paymentservice.mapper;

import org.mapstruct.*;
import org.nightingaale.paymentservice.model.dto.RefundTransactionDto;
import org.nightingaale.paymentservice.model.entity.RefundTransactionEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RefundTransactionMapper {
    RefundTransactionEntity toEntity(RefundTransactionDto refundTransactionDto);

    RefundTransactionDto toDto(RefundTransactionEntity refundTransactionEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    RefundTransactionEntity partialUpdate(RefundTransactionDto refundTransactionDto, @MappingTarget RefundTransactionEntity refundTransactionEntity);
}