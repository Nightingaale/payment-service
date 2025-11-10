package org.nightingaale.paymentservice.mapper.outbox;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutboxEventFactoryMapper {
    OutboxEventEntity fromPaymentTransaction(PaymentTransactionEntity transactionEntity);
}
