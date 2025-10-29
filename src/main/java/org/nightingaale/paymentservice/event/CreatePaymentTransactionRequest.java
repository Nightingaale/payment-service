package org.nightingaale.paymentservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentTransactionRequest {
    private Long paymentTransactionId;
    private PaymentTransactionStatus paymentStatus;
    private BigDecimal amount;
    private String currency;
    private PaymentMethodType type;
    private PaymentMethodProvider provider;
    private String maskedDetails;
}

