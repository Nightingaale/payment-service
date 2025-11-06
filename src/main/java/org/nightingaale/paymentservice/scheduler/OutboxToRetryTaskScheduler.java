package org.nightingaale.paymentservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.entity.outbox.RetryableTaskEntity;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;
import org.nightingaale.paymentservice.repository.outbox.OutboxRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxToRetryTaskScheduler {

    private final OutboxRepository outboxRepository;

    @Scheduled(fixedDelayString = "${scheduler.outbox-to-retryable.delay-ms}")
    public void createRetryableTaskFromOutbox() {
        Instant now = Instant.now();
        log.info("[Starting Outbox Scheduler at {}]", now);
        List<OutboxEventEntity> unprocessedEvents = outboxRepository.findPendingEventsForProcessing(now, Pageable.ofSize(30));

        if (unprocessedEvents.isEmpty()) {
            log.debug("[No unprocessed Outbox events found]");
            return;
        }

        try {
            unprocessedEvents.forEach(e -> e.setProcessed(true));
            outboxRepository.saveAll(unprocessedEvents);

        } catch (ObjectOptimisticLockingFailureException ex) {
            log.warn("[Some Outbox events were already processed by another scheduler]");
        }
    }
}
