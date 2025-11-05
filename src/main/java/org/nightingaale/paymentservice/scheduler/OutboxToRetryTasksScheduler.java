package org.nightingaale.paymentservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.entity.outbox.RetryableTaskEntity;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;
import org.nightingaale.paymentservice.repository.outbox.OutboxRepository;
import org.nightingaale.paymentservice.service.RetryableTaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxToRetryTasksScheduler {

    private final OutboxRepository outboxRepository;
    private final RetryableTaskService retryableTaskService;

    @Scheduled(fixedDelayString = "${scheduler.outbox-to-retryable.delay-ms}")
    public void createRetryableTaskFromOutbox() {

        List<OutboxEventEntity> unprocessedEvents = outboxRepository.findAll()
                .stream()
                .filter(e -> !e.isProcessed())
                .toList();

        if (unprocessedEvents.isEmpty()) {
            log.debug("[No unprocessed Outbox events found]");
            return;
        }

        log.info("[Found {} unprocessed Outbox events, creating RetryableTasks]", unprocessedEvents.size());

        List<RetryableTaskEntity> retryableTasks = retryableTaskService.createRetryableTasks(unprocessedEvents, RetryableTaskType.SEND_CREATE_DELIVERY_REQUEST);

        log.info("[Created {} RetryableTasks for Outbox events]", retryableTasks.size());
    }
}
