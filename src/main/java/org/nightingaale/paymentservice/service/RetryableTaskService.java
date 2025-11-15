package org.nightingaale.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.mapper.outbox.RetryableTaskMapper;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.entity.outbox.RetryableTaskEntity;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;
import org.nightingaale.paymentservice.repository.outbox.RetryableTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryableTaskService {
    private final RetryableTaskRepository retryableTaskRepository;
    private final RetryableTaskMapper retryableTaskMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RetryableTaskEntity> createRetryableTasks(List<OutboxEventEntity> outboxes, RetryableTaskType type) {
        var retryableTasks = outboxes.stream()
                .map(o -> retryableTaskMapper.toRetryableTask(o, type))
                .toList();
        return retryableTaskRepository.saveAll(retryableTasks);
    }
}
