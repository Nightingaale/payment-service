package org.nightingaale.paymentservice.repository;

import org.nightingaale.paymentservice.model.entity.PaymentMethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethodEntity, Long> {
}