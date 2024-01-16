package com.backend.core.exception;

public class DuplicateDocumentException extends RuntimeException {

    public DuplicateDocumentException(String message) {
        super(message);
    }

}
