package org.nightingaale.paymentservice.exception;

public class NotSavedException extends RuntimeException {
    public NotSavedException(String message) {
        super(message);
    }
}
