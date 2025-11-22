package org.nightingaale.paymentservice.service;

import lombok.RequiredArgsConstructor;
import org.nightingaale.paymentservice.mapper.RefundTransactionMapper;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.RefundTransactionEntity;
import org.nightingaale.paymentservice.repository.RefundTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundTransactionRepository refundTransactionRepository;
    private final RefundTransactionMapper refundTransactionMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveRefundTransaction(PaymentTransactionEntity transaction, String errorMessage) {
        RefundTransactionEntity refund = refundTransactionMapper.toRefund(transaction, errorMessage);
        refundTransactionRepository.save(refund);

    }
}

