package org.nightingaale.paymentservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;
import org.nightingaale.paymentservice.model.enums.converter.PaymentMethodProviderConverter;
import org.nightingaale.paymentservice.model.enums.converter.PaymentMethodTypeConverter;
import org.nightingaale.paymentservice.model.enums.converter.PaymentTransactionStatusConverter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments_log")
public class PaymentLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long logId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_transaction_id")
    private PaymentTransactionEntity paymentTransaction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "refund_transaction_id", nullable = true)
    private RefundTransactionEntity refundTransaction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id")
    private PaymentMethodEntity paymentMethod;


    @Convert(converter = PaymentTransactionStatusConverter.class)
    @Column(updatable = false)
    private PaymentTransactionStatus status;

    @Convert(converter = PaymentMethodProviderConverter.class)
    @Column(updatable = false)
    private PaymentMethodProvider provider;

    @Convert(converter = PaymentMethodTypeConverter.class)
    @Column(updatable = false)
    private PaymentMethodType methodType;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
