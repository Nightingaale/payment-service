package org.nightingaale.paymentservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nightingaale.paymentservice.exception.NotSavedException;
import org.nightingaale.paymentservice.mapper.RefundTransactionMapper;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.repository.RefundTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundTransactionRepository refundTransactionRepository;
    private final RefundTransactionMapper refundTransactionMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRefundTransaction(PaymentTransactionEntity transaction, String errorMessage) {
        try {
            var refund = refundTransactionMapper.toRefund(transaction, errorMessage);
            refundTransactionRepository.save(refund);
            log.info("[Payment failed. Information about transaction with ID was saved: {}]", transaction.getId());
        } catch (Exception e) {
            log.error("[Payment with transaction ID: {}, wasn't saved]", transaction.getId(), e);
            throw new NotSavedException(errorMessage);
        }
    }
}

