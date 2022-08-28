package com.example.microblinkdemo.exception;

public class MicroblinkException extends RuntimeException {

    public MicroblinkException(String message) {
        super(message);
    }

    public MicroblinkException(String message, Throwable cause) {
        super(message, cause);
    }
}
