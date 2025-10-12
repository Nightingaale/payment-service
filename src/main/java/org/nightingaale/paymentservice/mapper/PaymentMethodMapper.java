package org.nightingaale.paymentservice.mapper;

import org.mapstruct.*;
import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.entity.PaymentMethodEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentMethodMapper {
    PaymentMethodEntity toEntity(PaymentMethodDto paymentMethodDto);

    PaymentMethodDto toDto(PaymentMethodEntity paymentMethodEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PaymentMethodEntity partialUpdate(PaymentMethodDto paymentMethodDto, @MappingTarget PaymentMethodEntity paymentMethodEntity);
}