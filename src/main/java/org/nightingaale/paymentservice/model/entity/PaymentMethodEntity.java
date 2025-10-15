package org.nightingaale.paymentservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.converter.PaymentMethodProviderConverter;
import org.nightingaale.paymentservice.model.enums.converter.PaymentMethodTypeConverter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_methods")
public class PaymentMethodEntity extends BaseEntity {

    @NotNull
    @Convert(converter = PaymentMethodTypeConverter.class)
    @Column(updatable = false)
    private PaymentMethodType type;

    @NotNull
    @Convert(converter = PaymentMethodProviderConverter.class)
    @Column(updatable = false)
    private PaymentMethodProvider provider;

    @NotNull
    @Column(length = 16, updatable = false, nullable = true)
    private String maskedDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_transaction_id", nullable = false)
    private PaymentTransactionEntity paymentTransaction;

}