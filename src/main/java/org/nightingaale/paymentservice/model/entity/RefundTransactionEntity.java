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
import org.nightingaale.paymentservice.util.CreditCardConverter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "refunds_transaction")
public class RefundTransactionEntity extends BaseEntity {

    @NotNull
    @Column(updatable = false, columnDefinition = "uuid")
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

    @NotNull
    @Convert(converter = CreditCardConverter.class)
    @Column(length = 16, updatable = false, nullable = false)
    private String maskedDetails;

    @PrePersist
    public void generateRefundTransactionId() {
        if (refundTransactionId == null || refundTransactionId.isBlank()) {
            refundTransactionId = UUID.randomUUID().toString();
        }
    }
}
