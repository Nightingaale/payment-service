package org.nightingaale.paymentservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.mapper.outbox.RetryableTaskMapper;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.entity.outbox.RetryableTaskEntity;
import org.nightingaale.paymentservice.model.enums.outbox.RetryableTaskType;
import org.nightingaale.paymentservice.repository.outbox.RetryableTaskRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RetryableTaskService {
    private final RetryableTaskRepository retryableTaskRepository;
    private final RetryableTaskMapper retryableTaskMapper;

    @Value("${retryabletask.timeoutInSeconds}")
    private Integer timeoutInSeconds;

    @Value("${retryabletask.limit}")
    private Integer limit;

    @Transactional
    public List<RetryableTaskEntity> createRetryableTasks(List<OutboxEventEntity> outboxes, RetryableTaskType type) {
        var retryableTasks = outboxes.stream()
                .map(o -> retryableTaskMapper.toRetryableTask(o, type))
                .toList();
        return retryableTaskRepository.saveAll(retryableTasks);
    }
}
