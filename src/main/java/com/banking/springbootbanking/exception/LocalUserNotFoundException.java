package com.banking.springbootbanking.exception;

public class LocalUserNotFoundException extends RuntimeException {
    public LocalUserNotFoundException(String message) {
        super(message);
    }
}
