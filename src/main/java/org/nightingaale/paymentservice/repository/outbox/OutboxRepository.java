package org.nightingaale.paymentservice.repository.outbox;

import jakarta.persistence.LockModeType;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEventEntity, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM OutboxEventEntity o " +
            "WHERE o.processed = false " +
            "AND o.createdAt <= :until " +
            "ORDER BY o.createdAt ASC")
    List<OutboxEventEntity> findPendingEventsForProcessing(Instant until, Pageable pageable);
}
