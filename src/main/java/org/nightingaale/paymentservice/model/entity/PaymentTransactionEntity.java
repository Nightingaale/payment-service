package org.nightingaale.paymentservice.model.entity;

import com.thoughtworks.xstream.converters.extended.CurrencyConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;
import org.nightingaale.paymentservice.model.enums.converter.PaymentTransactionStatusConverter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class PaymentTransactionEntity extends BaseEntity {

    @NotNull
    @Column(unique = true, updatable = false)
    private Long paymentTransactionId;

    @Convert(converter = PaymentTransactionStatusConverter.class)
    @NotNull
    private PaymentTransactionStatus paymentStatus;

    @NotNull
    @Column(updatable = false)
    private BigDecimal amount;

    @NotNull
    @Convert(converter = CurrencyConverter.class)
    @Column(updatable = false)
    private String currency;
}
