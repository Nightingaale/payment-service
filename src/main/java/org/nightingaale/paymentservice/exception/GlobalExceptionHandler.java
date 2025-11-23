package org.nightingaale.paymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(FailedTransactionException.class)
    public ResponseEntity<?> handleFailedTransaction(FailedTransactionException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("Error", ex.getMessage()));
    }
}
