package org.nightingaale.paymentservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class PaymentTransactionEntity extends BaseEntity {

    @NotNull
    @Column(unique = true, updatable = false)
    private Long paymentTransactionId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentTransactionStatus paymentStatus;

    @NotNull
    @Column(updatable = false)
    private BigDecimal amount;

    @NotNull
    @Column(updatable = false)
    private String currency;
}
