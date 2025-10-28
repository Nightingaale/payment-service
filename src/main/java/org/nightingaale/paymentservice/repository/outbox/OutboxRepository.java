package org.nightingaale.paymentservice.repository.outbox;

import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxEventEntity, UUID> {
}
