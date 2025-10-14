package org.nightingaale.paymentservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Convert(converter = RefundTransactionStatusConverter.class)
    @NotNull
    private RefundTransactionStatus refundStatus;

    @NotNull
    @Column(updatable = false)
    private String errorMessage;
}
