package org.nightingaale.paymentservice.repository;

import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, String> {
    List<PaymentTransactionEntity> findAllByUserId(String userId);
    List<PaymentTransactionEntity> findByUserIdAndCreatedAtBetween(String userId, LocalDateTime start, LocalDateTime end);
}
