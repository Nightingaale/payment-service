package org.nightingaale.paymentservice.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nightingaale.paymentservice.exception.NotSavedException;
import org.nightingaale.paymentservice.mapper.RefundTransactionMapper;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.RefundTransactionEntity;
import org.nightingaale.paymentservice.repository.RefundTransactionRepository;
import org.nightingaale.paymentservice.service.RefundService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefundServiceTest {

    @InjectMocks
    private RefundService refundService;

    @Mock
    private RefundTransactionMapper refundTransactionMapper;

    @Mock
    private RefundTransactionRepository refundTransactionRepository;

    @Test
    void getRefund_save() {
        // Arrange
        var fakeTransactionEntity = new PaymentTransactionEntity();
        var errorMessage = "Payment failed";
        var refundTransactionEntity = new RefundTransactionEntity();

        // Act
        when(refundTransactionMapper.toRefund(fakeTransactionEntity, errorMessage)).thenReturn(refundTransactionEntity);

        refundService.saveRefundTransaction(fakeTransactionEntity, errorMessage);

        // Assert
        verify(refundTransactionMapper).toRefund(fakeTransactionEntity, errorMessage);
        verify(refundTransactionRepository).save(refundTransactionEntity);
    }

    @Test
    void getRefund_save_failed() {
        var fakeTransactionEntity = new PaymentTransactionEntity();
        var refundTransactionEntity = new RefundTransactionEntity();
        var errorMessage = "Payment failed";

        when(refundTransactionMapper.toRefund(fakeTransactionEntity, errorMessage))
                .thenReturn(refundTransactionEntity);

        doThrow(new RuntimeException("App or Postgres error"))
                .when(refundTransactionRepository).save(refundTransactionEntity);

        NotSavedException exception = assertThrows(NotSavedException.class, () -> refundService.saveRefundTransaction(fakeTransactionEntity, errorMessage));

        assertEquals(errorMessage, exception.getMessage());
    }
}
