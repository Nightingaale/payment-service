package org.nightingaale.paymentservice.repository;

import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {
}
