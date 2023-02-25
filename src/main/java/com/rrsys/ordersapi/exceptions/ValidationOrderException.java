package com.rrsys.ordersapi.exceptions;

public class ValidationOrderException extends RuntimeException {
    public ValidationOrderException(String message) {
        super(message);
    }
}
