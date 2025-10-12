package org.nightingaale.paymentservice.mapper;

import org.mapstruct.*;
import org.nightingaale.paymentservice.model.dto.PaymentLogDto;
import org.nightingaale.paymentservice.model.entity.PaymentLogEntity;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentLogMapper {
    PaymentLogEntity toEntity(PaymentLogDto paymentLongDto);

    PaymentLogDto toDto(PaymentLogEntity paymentLogEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PaymentLogEntity partialUpdate(PaymentLogDto paymentLongDto, @MappingTarget PaymentLogEntity paymentLogEntity);
}