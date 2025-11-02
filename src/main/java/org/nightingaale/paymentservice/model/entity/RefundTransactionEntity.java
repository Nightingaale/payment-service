package org.nightingaale.paymentservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nightingaale.paymentservice.model.enums.CurrencyStatus;
import org.nightingaale.paymentservice.model.enums.RefundTransactionStatus;
import org.nightingaale.paymentservice.model.enums.converter.CurrencyStatusConverter;
import org.nightingaale.paymentservice.model.enums.converter.RefundTransactionStatusConverter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refunds_transaction")
public class RefundTransactionEntity extends BaseEntity {

    @NotNull
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, unique = true)
    private String refundTransactionId;

    @NotNull
    @Column(updatable = false)
    private BigDecimal refundedAmount;

    @NotNull
    @Convert(converter = RefundTransactionStatusConverter.class)
    @Column(updatable = false)
    private RefundTransactionStatus refundStatus;

    @NotNull
    @Convert(converter = CurrencyStatusConverter.class)
    @Column(updatable = false)
    private CurrencyStatus currency;

    @NotNull
    @Column(updatable = false)
    private String errorMessage;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentTransactionEntity paymentTransaction;
}
