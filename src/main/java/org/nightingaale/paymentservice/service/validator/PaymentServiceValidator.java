package org.nightingaale.paymentservice.service.validator;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
@RequiredArgsConstructor
public class PaymentServiceValidator {

    private final Validator validator;
}
