package com.example.pos.domain.exception;

public class InsufficientPaymentException extends DomainException {
    public InsufficientPaymentException(String message) {
        super(message);
    }
}
