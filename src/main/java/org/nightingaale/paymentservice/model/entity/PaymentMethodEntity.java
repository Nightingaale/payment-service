package org.nightingaale.paymentservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethodEntity extends BaseEntity {

    @NotNull
    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private PaymentMethodType type;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(updatable = false)
    private PaymentMethodProvider provider;

    @NotNull
    @Column(updatable = false)
    private String maskedDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_transaction_id", nullable = false)
    private PaymentTransactionEntity paymentTransaction;

}