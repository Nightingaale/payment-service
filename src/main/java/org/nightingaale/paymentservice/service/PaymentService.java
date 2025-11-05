package org.nightingaale.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.client.UserServiceClient;
import org.nightingaale.paymentservice.event.CreatePaymentTransactionRequest;
import org.nightingaale.paymentservice.handler.api.PaymentMethodHandler;
import org.nightingaale.paymentservice.mapper.PaymentTransactionMapper;
import org.nightingaale.paymentservice.mapper.PaymentTransactionRequestMapper;
import org.nightingaale.paymentservice.mapper.outbox.OutboxEventFactoryMapper;
import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.repository.PaymentTransactionRepository;
import org.nightingaale.paymentservice.repository.outbox.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentTransactionRequestMapper transactionRequestMapper;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final List<PaymentMethodHandler> paymentMethodHandlers;
    private final OutboxRepository outboxRepository;
    private final OutboxEventFactoryMapper outboxEventFactoryMapper;
    private final UserServiceClient userServiceClient;

    @Transactional
    public void createPaymentTransaction(CreatePaymentTransactionRequest request) {

        request.setUserId(userServiceClient.sendId(request).getUserId());
        log.info("[Received userId from user-service: {}]", request.getUserId());

        try {
            String paymentTransactionId = UUID.randomUUID().toString();

            log.info("[Start processing payment transaction ID for user with ID: {}, {}]", request.getPaymentTransactionId(), request.getUserId());

            PaymentMethodType methodType = request.getPaymentMethodType();
            PaymentMethodHandler handler = paymentMethodHandlers.stream()
                    .filter(h -> h.getPaymentMethodType() == methodType)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("[Unsupported payment method type: ]" + methodType));

            PaymentMethodDto processedDetails = handler.process(request.getPaymentMethod());

            PaymentTransactionEntity transactionEntity = transactionRequestMapper.toEntity(request);

            transactionEntity.setMaskedDetails(processedDetails.maskedDetails());
            transactionEntity.setPaymentTransactionId(paymentTransactionId);
            paymentTransactionRepository.save(transactionEntity);

            OutboxEventEntity outboxEventEntity = outboxEventFactoryMapper.fromPaymentTransaction(transactionEntity);
            outboxRepository.save(outboxEventEntity);

            log.info("[Payment transaction saved: {}]", transactionEntity.getId());
        } catch (RuntimeException e) {
            log.error("[Payment transaction with ID: {} could not be created]", request.getPaymentTransactionId(), e);
            throw e;
        }
    }

    @Transactional
    public Optional<?> getPaymentTransaction(String paymentTransactionId) {
        return paymentTransactionRepository.findByPaymentTransactionId(paymentTransactionId)
                .map(paymentTransactionMapper::toDto);
    }
}
