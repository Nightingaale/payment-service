package org.nightingaale.paymentservice.service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nightingaale.paymentservice.client.UserServiceClient;
import org.nightingaale.paymentservice.event.CreatePaymentTransactionRequest;
import org.nightingaale.paymentservice.event.feign.CreateUserIdRequest;
import org.nightingaale.paymentservice.exception.FailedTransactionException;
import org.nightingaale.paymentservice.handler.api.PaymentMethodHandler;
import org.nightingaale.paymentservice.handler.provider.PaymentProviderHandler;
import org.nightingaale.paymentservice.mapper.PaymentTransactionMapper;
import org.nightingaale.paymentservice.mapper.PaymentTransactionRequestMapper;
import org.nightingaale.paymentservice.mapper.outbox.OutboxEventFactoryMapper;
import org.nightingaale.paymentservice.model.dto.PaymentMethodDto;
import org.nightingaale.paymentservice.model.entity.PaymentTransactionEntity;
import org.nightingaale.paymentservice.model.entity.outbox.OutboxEventEntity;
import org.nightingaale.paymentservice.model.enums.PaymentMethodProvider;
import org.nightingaale.paymentservice.model.enums.PaymentMethodType;
import org.nightingaale.paymentservice.model.enums.PaymentPeriod;
import org.nightingaale.paymentservice.repository.PaymentTransactionRepository;
import org.nightingaale.paymentservice.repository.outbox.OutboxRepository;
import org.nightingaale.paymentservice.service.PaymentService;
import org.nightingaale.paymentservice.service.RefundService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentService paymentService;

    @Mock
    PaymentTransactionRepository paymentTransactionRepository;

    @Mock
    PaymentTransactionRequestMapper paymentTransactionRequestMapper;

    @Mock
    PaymentTransactionMapper paymentTransactionMapper;

    @Mock
    PaymentProviderHandler paymentProviderHandler;

    @Mock
    UserServiceClient userServiceClient;

    @Mock
    OutboxEventFactoryMapper outboxEventFactoryMapper;

    @Mock
    OutboxRepository outboxRepository;

    @Mock
    PaymentMethodHandler paymentMethodHandler;

    @Mock
    RefundService refundService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createPaymentTransaction_success() {

        // Arrange
        var fakeRequest = new CreatePaymentTransactionRequest();
        fakeRequest.setPaymentMethodType(PaymentMethodType.CREDIT_CARD);

        var fakeResponse = new CreateUserIdRequest();
        fakeResponse.setUserId(UUID.randomUUID().toString());

        var fakeEntity = new PaymentTransactionEntity();
        var fakeOutbox = new OutboxEventEntity();
        var fakePaymentMethod = new PaymentMethodDto(UUID.randomUUID().toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                PaymentMethodType.CREDIT_CARD,
                PaymentMethodProvider.VISA,
                "****1111");
        fakeRequest.setPaymentMethod(fakePaymentMethod);

        // Act
        when(userServiceClient.sendId(fakeRequest)).thenReturn(fakeResponse);
        when(paymentMethodHandler.getPaymentMethodType()).thenReturn(PaymentMethodType.CREDIT_CARD);
        when(paymentMethodHandler.process(any())).thenReturn(fakePaymentMethod);
        when(paymentTransactionRequestMapper.toEntity(fakeRequest)).thenReturn(fakeEntity);
        when(paymentProviderHandler.getProviderByCardNumber(anyString())).thenReturn(PaymentMethodProvider.VISA);
        when(outboxEventFactoryMapper.fromPaymentTransaction(fakeEntity)).thenReturn(fakeOutbox);

        paymentService = new PaymentService(
                paymentTransactionRepository,
                paymentTransactionRequestMapper,
                paymentTransactionMapper,
                List.of(paymentMethodHandler),
                paymentProviderHandler,
                userServiceClient,
                outboxEventFactoryMapper,
                outboxRepository,
                refundService
        );

        // Assert
        paymentService.createPaymentTransaction(fakeRequest);
        verify(paymentTransactionRepository).save(fakeEntity);
        verify(outboxRepository).save(fakeOutbox);
    }

    @Test
    void getPaymentTransactionsByUserId() {
        // Arrange
        var fakeUserId = UUID.randomUUID().toString();

        // Act
        when(paymentTransactionRepository.findAllByUserId(fakeUserId)).thenReturn(List.of());
        paymentService.getPaymentTransactionsByUserId(fakeUserId);

        // Assert
        verify(paymentTransactionRepository).findAllByUserId(fakeUserId);
    }

    @Test
    void getPaymentsByPeriod() {
        // Arrange
        var fakeUserId = UUID.randomUUID().toString();
        var fakePeriod = PaymentPeriod.TODAY;

        // Act
        when(paymentTransactionRepository.findByUserIdAndCreatedAtBetween(eq(fakeUserId), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(List.of());
        paymentService.getPaymentsByPeriod(fakeUserId, fakePeriod);

        // Assert
        verify(paymentTransactionRepository).findByUserIdAndCreatedAtBetween(eq(fakeUserId), any(LocalDateTime.class), any(LocalDateTime.class));
    }

    @Test
    void createPaymentTransaction_failure_refund() {

        // Arrange
        var fakeRequest = new CreatePaymentTransactionRequest();
        fakeRequest.setPaymentMethodType(PaymentMethodType.CREDIT_CARD);

        var fakeResponse = new CreateUserIdRequest();
        fakeResponse.setUserId(UUID.randomUUID().toString());

        var fakeEntity = new PaymentTransactionEntity();

        // Act
        when(userServiceClient.sendId(fakeRequest)).thenReturn(fakeResponse);

        when(paymentTransactionRequestMapper.toEntity(fakeRequest)).thenReturn(fakeEntity);

        when(paymentMethodHandler.getPaymentMethodType()).thenReturn(PaymentMethodType.CREDIT_CARD);
        when(paymentMethodHandler.process(any())).thenThrow(new FailedTransactionException("Payment failed"));

        paymentService = new PaymentService(
                paymentTransactionRepository,
                paymentTransactionRequestMapper,
                paymentTransactionMapper,
                List.of(paymentMethodHandler),
                paymentProviderHandler,
                userServiceClient,
                outboxEventFactoryMapper,
                outboxRepository,
                refundService
        );


        // Exception
        FailedTransactionException ex = assertThrows(FailedTransactionException.class, () -> paymentService.createPaymentTransaction(fakeRequest));
        assertEquals("Payment failed", ex.getMessage());

        verify(refundService).saveRefundTransaction(eq(fakeEntity), eq("Payment failed"));

        // Do not call repos
        verify(paymentTransactionRepository, never()).save(any());
        verify(outboxRepository, never()).save(any());
    }
}