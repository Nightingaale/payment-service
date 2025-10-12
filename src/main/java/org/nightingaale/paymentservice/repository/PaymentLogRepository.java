package org.nightingaale.paymentservice.repository;

import org.nightingaale.paymentservice.model.entity.PaymentLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentLogRepository extends JpaRepository<PaymentLogEntity, Long> {
}
