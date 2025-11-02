package org.nightingaale.paymentservice.repository;

import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, String> {
    Optional<PaymentTransactionEntity> findByPaymentTransactionId(String paymentTransactionId);
}
