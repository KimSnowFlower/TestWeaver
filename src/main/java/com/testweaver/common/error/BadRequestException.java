package com.testweaver.common.error;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}
