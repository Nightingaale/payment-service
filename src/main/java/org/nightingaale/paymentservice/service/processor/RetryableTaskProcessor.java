package org.nightingaale.paymentservice.service.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.model.entity.outbox.RetryableTaskEntity;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskStatus;
import org.nightingaale.paymentservice.repository.outbox.RetryableTaskRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetryableTaskProcessor {

    private final RetryableTaskRepository retryableTaskRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processTask(RetryableTaskEntity task) {
        log.info("Processing retryable task {} of type {}", task.getId(), task.getType());

        try {
            task.setStatus(RetryableTaskStatus.IN_PROGRESS);
            task.setUpdatedAt(Instant.now());
            retryableTaskRepository.save(task);

            switch (task.getType()) {
                case SEND_CREATE_NOTIFICATION_REQUEST -> processCreateNotification(task);
                case SEND_CREATE_DELIVERY_REQUEST -> processCreateDelivery(task);
                default -> throw new IllegalStateException("Unexpected task type: " + task.getType());
            }

            task.setStatus(RetryableTaskStatus.SUCCESS);
            task.setUpdatedAt(Instant.now());
            retryableTaskRepository.save(task);

            log.info("Task {} processed successfully", task.getId());

        } catch (Exception e) {
            log.error("Error processing task {}: {}", task.getId(), e.getMessage(), e);
            task.setStatus(RetryableTaskStatus.RETRYING);
            task.setRetryTime(Instant.now().plusSeconds(30));
            retryableTaskRepository.save(task);
        }
    }

    public void processCreateNotification(RetryableTaskEntity task) {
        log.info("Sending create notification request for task {}", task.getId());
    }

    public void processCreateDelivery(RetryableTaskEntity task) {
        log.info("Sending create delivery request for task {}", task.getId());
    }
}
