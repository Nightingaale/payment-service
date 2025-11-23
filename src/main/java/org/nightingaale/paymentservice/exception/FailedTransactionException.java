package org.nightingaale.paymentservice.exception;

public class FailedTransactionException extends RuntimeException {
    public FailedTransactionException(String message) {
        super(message);
    }
}
