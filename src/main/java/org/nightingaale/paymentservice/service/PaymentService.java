package org.nightingaale.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.client.UserServiceClient;
import org.nightingaale.paymentservice.event.CreatePaymentTransactionRequest;
import org.nightingaale.paymentservice.handler.api.PaymentMethodHandler;
import org.nightingaale.paymentservice.handler.provider.PaymentProviderHandler;
import org.nightingaale.paymentservice.mapper.PaymentLogMapper;
import org.nightingaale.paymentservice.mapper.PaymentTransactionMapper;
import org.nightingaale.paymentservice.mapper.PaymentTransactionRequestMapper;
import org.nightingaale.paymentservice.mapper.outbox.OutboxEventFactoryMapper;
import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.entity.PaymentLogEntity;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;
import org.nightingaale.paymentservice.repository.PaymentLogRepository;
import org.nightingaale.paymentservice.repository.PaymentTransactionRepository;
import org.nightingaale.paymentservice.repository.outbox.OutboxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentTransactionRequestMapper transactionRequestMapper;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final List<PaymentMethodHandler> paymentMethodHandlers;
    private final PaymentProviderHandler paymentProviderHandler;
    private final UserServiceClient userServiceClient;
    private final OutboxEventFactoryMapper outboxEventFactoryMapper;
    private final OutboxRepository outboxRepository;
    private final PaymentLogMapper paymentLogMapper;
    private final PaymentLogRepository paymentLogRepository;

    @Transactional
    public void createPaymentTransaction(CreatePaymentTransactionRequest request) {

        request.setUserId(userServiceClient.sendId(request).getUserId());
        log.info("[Received userId from user-service: {}]", request.getUserId());

        try {
            log.info("[Start processing payment transaction for user with ID: {}]", request.getUserId());

            PaymentMethodType methodType = request.getPaymentMethodType();
            PaymentMethodHandler handler = paymentMethodHandlers.stream()
                    .filter(h -> h.getPaymentMethodType() == methodType)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("[Unsupported payment method type: ]" + methodType));

            PaymentMethodDto processedDetails = handler.process(request.getPaymentMethod());
            PaymentTransactionEntity transactionEntity = transactionRequestMapper.toEntity(request);

            transactionEntity.setMaskedDetails(processedDetails.maskedDetails());

            PaymentMethodProvider provider = paymentProviderHandler.getProviderByCardNumber(request.getPaymentMethod().maskedDetails());
            transactionEntity.setProvider(provider);

            transactionEntity.setPaymentStatus(PaymentTransactionStatus.PROCESSING);

            paymentTransactionRepository.save(transactionEntity);

            PaymentLogEntity paymentLog = paymentLogMapper.toLog(transactionEntity);
            paymentLogRepository.save(paymentLog);

            OutboxEventEntity outbox = outboxEventFactoryMapper.fromPaymentTransaction(transactionEntity);
            outboxRepository.save(outbox);

            log.info("[Payment transaction saved: {}]", transactionEntity.getId());
        } catch (RuntimeException e) {
            log.error("[Payment transaction with ID: {} could not be created]", request.getPaymentTransactionId(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<?> getPaymentTransactionsByUserId(String userId) {
        return paymentTransactionRepository.findAllByUserId(userId)
                .stream()
                .map(paymentTransactionMapper::toDto)
                .toList();
    }
}
