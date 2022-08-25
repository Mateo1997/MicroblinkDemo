package com.example.microblinkdemo.util;

public class ResponseConstants {

    private ResponseConstants() {
        throw new IllegalStateException("ResponseConstants class");
    }

    public static final String ERROR_MISSING_REQUEST_PARAMETER = "MISSING_REQUEST_PARAMETER";
    public static final String ERROR_USER_NOT_FOUND = "User not found";
    public static final String ERROR_INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
}
