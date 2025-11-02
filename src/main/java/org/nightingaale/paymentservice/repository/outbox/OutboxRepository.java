package org.nightingaale.paymentservice.repository.outbox;

import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OutboxEventEntity, String> {
}
