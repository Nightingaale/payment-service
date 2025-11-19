package org.nightingaale.paymentservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.enums.outbox.OutboxType;
import org.nightingaale.paymentservice.repository.outbox.OutboxRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPoller {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void processOutboxEvents() {
        Instant now = Instant.now();
        log.info("[Starting Outbox Poller at {}]", now);
        List<OutboxEventEntity> events = outboxRepository.findByType(OutboxType.NEW);
        for (OutboxEventEntity event : events) {
            try {
                kafkaTemplate.send("payment-transaction-create", event.getAggregateId(), event.getPayload()).get();
                event.setType(OutboxType.PENDING);
                outboxRepository.save(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
