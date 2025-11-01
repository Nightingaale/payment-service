package org.nightingaale.paymentservice.event;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.PaymentTransactionStatus;
import org.nightingaale.paymentservice.util.CreditCardJsonSerializer;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentTransactionRequest {
    private Long paymentTransactionId;
    private String userId;
    private PaymentTransactionStatus paymentStatus;
    private BigDecimal amount;
    private String currency;
    private PaymentMethodType type;
    private PaymentMethodProvider provider;

    private PaymentMethodType paymentMethodType;
    private PaymentMethodDto paymentMethod;

    @JsonSerialize(using = CreditCardJsonSerializer.class)
    private String maskedDetails;
}

