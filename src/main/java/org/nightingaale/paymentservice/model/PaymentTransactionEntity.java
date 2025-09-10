package org.nightingaale.paymentservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nightingaale.paymentservice.enums.PaymentTransactionStatus;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class PaymentTransactionEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "transaction_id")
    private RefundTransactionEntity transactionId;

    @NotNull
    private PaymentTransactionStatus paymentStatus;

    @NotNull
    private String currency;
}
