package org.nightingaale.paymentservice.repository.outbox;
import org.nightingaale.paymentservice.model.entity.outbox.RetryableTaskEntity;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskStatus;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface RetryableTaskRepository extends JpaRepository<RetryableTaskEntity, UUID> {

    @Query("SELECT r from RetryableTaskEntity r where r.type= :type " +
            "AND r.retryTime<= :retryTime " +
            "AND r.status= :status " +
            "order by r.retryTime asc")

    List<RetryableTaskEntity> findRetryableTaskForProcessing(RetryableTaskType type, Instant retryTime, RetryableTaskStatus status, Pageable pageable);
}
