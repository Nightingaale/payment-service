package org.nightingaale.paymentservice.repository.outbox;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.enums.outbox.OutboxType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEventEntity, String> {

    @Query("SELECT o FROM OutboxEventEntity o " +
            "WHERE o.processed = false " +
            "AND o.createdAt <= :until " +
            "ORDER BY o.createdAt ASC")

    List<OutboxEventEntity> findPendingEventsForProcessing(Instant until, Pageable pageable, OutboxType type);
    List<OutboxEventEntity> findByType(OutboxType type);
}
