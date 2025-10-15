package org.nightingaale.paymentservice.model.entity;

import com.thoughtworks.xstream.converters.extended.CurrencyConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nightingaale.paymentservice.model.enums.CurrencyStatus;
import org.nightingaale.paymentservice.model.enums.RefundTransactionStatus;
import org.nightingaale.paymentservice.model.enums.converter.RefundTransactionStatusConverter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "refunds")
public class RefundTransactionEntity extends BaseEntity {

    @NotNull
    @Column(updatable = false, unique = true)
    private Long refundTransactionId;

    @NotNull
    @Column(updatable = false)
    private BigDecimal refundedAmount;

    @NotNull
    @Convert(converter = RefundTransactionStatusConverter.class)
    @Column(updatable = false)
    private RefundTransactionStatus refundStatus;

    @NotNull
    @Convert(converter = CurrencyConverter.class)
    @Column(updatable = false)
    private CurrencyStatus currency;

    @NotNull
    @Column(updatable = false)
    private String errorMessage;
}
