package org.nightingaale.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.model.entity.outbox.RetryableTaskEntity;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskStatus;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;
import org.nightingaale.paymentservice.repository.outbox.RetryableTaskRepository;
import org.nightingaale.paymentservice.service.processor.RetryableTaskProcessor;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryableTaskService {

    private final RetryableTaskRepository retryableTaskRepository;
    private final RetryableTaskProcessor retryableTaskProcessor;

    @Scheduled(fixedDelayString = "${scheduler.retryable-task.delay-ms}")
    @Transactional
    public void processRetryableTasks() {
        Instant now = Instant.now();
        log.info("Starting scheduled retryable task processing at {}", now);
        for (RetryableTaskType type : RetryableTaskType.values()) {
            try {
                List<RetryableTaskEntity> tasks = retryableTaskRepository.findRetryableTaskForProcessing(
                        type,
                        now,
                        RetryableTaskStatus.IN_PROGRESS,
                        PageRequest.of(0, 30)
                );

                if (tasks.isEmpty()) {
                    log.debug("No retryable tasks found for type {}", type);
                    continue;
                }

                log.info("Found {} tasks for type {}", tasks.size(), type);
                for (RetryableTaskEntity task : tasks) {
                    try {
                        retryableTaskProcessor.processTask(task);
                    } catch (Exception e) {
                        log.error("Failed to process task {}: {}", task.getId(), e.getMessage(), e);
                    }
                }
            } catch (Exception e) {
                log.error("Error while processing retryable tasks for type {}: {}", type, e.getMessage(), e);
            }
        }
        log.info("Finished scheduled retryable task processing");
    }
}
