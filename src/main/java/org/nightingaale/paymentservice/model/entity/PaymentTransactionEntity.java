package org.nightingaale.paymentservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;
import org.nightingaale.paymentservice.model.enums.converter.CurrencyStatusConverter;
import org.nightingaale.paymentservice.model.enums.converter.PaymentMethodProviderConverter;
import org.nightingaale.paymentservice.model.enums.converter.PaymentMethodTypeConverter;
import org.nightingaale.paymentservice.model.enums.converter.PaymentTransactionStatusConverter;
import org.nightingaale.paymentservice.util.CreditCardConverter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments_transaction")
public class PaymentTransactionEntity extends BaseEntity {

    @NotNull
    @Column(updatable = false)
    private String paymentTransactionId;

    @Convert(converter = PaymentTransactionStatusConverter.class)
    @NotNull
    private PaymentTransactionStatus paymentStatus;

    @NotNull
    @Column(updatable = false)
    private BigDecimal amount;

    @NotNull
    @Convert(converter = CurrencyStatusConverter.class)
    @Column(updatable = false)
    private String currency;

    @NotNull
    @Convert(converter = PaymentMethodTypeConverter.class)
    @Column(updatable = false)
    private PaymentMethodType type;

    @NotNull
    @Convert(converter = PaymentMethodProviderConverter.class)
    @Column(updatable = false)
    private PaymentMethodProvider provider;

    @NotNull
    @Convert(converter = CreditCardConverter.class)
    @Column(length = 16, updatable = false, nullable = false)
    private String maskedDetails;

    @PrePersist
    public void generatePaymentTransactionId() {
        if (paymentTransactionId == null || paymentTransactionId.isBlank()) {
            paymentTransactionId = UUID.randomUUID().toString();
        }
    }
}
