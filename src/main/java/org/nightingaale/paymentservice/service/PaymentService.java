package org.nightingaale.paymentservice.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.event.CreatePaymentTransactionRequest;
import org.nightingaale.paymentservice.handler.api.PaymentMethodHandler;
import org.nightingaale.paymentservice.mapper.PaymentTransactionMapper;
import org.nightingaale.paymentservice.mapper.PaymentTransactionRequestMapper;
import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.dto.PaymentTransactionDto;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.repository.PaymentTransactionRepository;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String, CreatePaymentTransactionRequest> paymentTransactionKafkaTemplate;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final List<PaymentMethodHandler> paymentMethodHandlers;

    @Transactional
    public void createPaymentTransaction(CreatePaymentTransactionRequest request) {
        try {
            if (paymentTransactionRepository.existsById(request.getPaymentTransactionId())) {
                log.warn("[Payment transaction with ID: {} already exists]", request.getPaymentTransactionId());
                return;
            }

            log.info("[Start processing payment transaction ID: {}]", request.getPaymentTransactionId());

            PaymentMethodType methodType = request.getPaymentMethodType();
            PaymentMethodHandler handler = paymentMethodHandlers.stream()
                    .filter(h -> h.getPaymentMethodType() == methodType)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("[Unsupported payment method type: ]" + methodType));

            PaymentMethodDto processedDetails = handler.process(request.getPaymentMethod());

            PaymentTransactionEntity transactionEntity = transactionRequestMapper.toEntity(request);
            transactionEntity.setMaskedDetails(processedDetails.maskedDetails());
            paymentTransactionRepository.save(transactionEntity);

            log.info("[Payment transaction saved: {}]", transactionEntity.getId());

            paymentTransactionKafkaTemplate.send("payment-transaction-create", request);
            log.info("[Kafka event sent transactionID: {}]", request.getPaymentTransactionId());

        } catch (RuntimeException e) {
            log.error("[Payment transaction with ID: {} could not be created]", request.getPaymentTransactionId(), e);
            throw e;
        }
    }

    @Transactional
    public Optional<PaymentTransactionDto> getPaymentTransaction(Long paymentTransactionId) {
        return paymentTransactionRepository.findById(paymentTransactionId)
                .map(paymentTransactionMapper::toDto);
    }
}
