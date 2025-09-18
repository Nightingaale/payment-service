package org.nightingaale.paymentservice.repository;

import org.nightingaale.paymentservice.model.entity.RefundTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundTransactionRepository extends JpaRepository<RefundTransactionEntity, Long> {
}
