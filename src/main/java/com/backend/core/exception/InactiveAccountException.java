package com.backend.core.exception;

public class InactiveAccountException extends RuntimeException {

    public InactiveAccountException(String message) {
        super(message);
    }

}
