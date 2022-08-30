package com.example.microblinkdemo.util;

public class ResponseConstants {

    private ResponseConstants() {
        throw new IllegalStateException("ResponseConstants class");
    }

    public static final String ERROR_MISSING_REQUEST_PARAMETER = "Missing request parameter";
    public static final String ERROR_DUPLICATE_COPY_BOOKS_FOUND = "Duplicate copy book ids are found in collection";
    public static final String ERROR_USER_NOT_FOUND = "User not found";
    public static final String ERROR_BOOK_COPY_NOT_FOUND = "Copy of the book was not found";
    public static final String ERROR_BOOK_COPY_ALREADY_BORROWED = "The book copy has been already borrowed";
    public static final String ERROR_INTERNAL_SERVER_ERROR = "Internal server error";

    //Microblink exceptions
    public static final String ERROR_MICROBLINK_SERVICE_UNAVAILABLE = "MICROBLINK_SERVICE_UNAVAILABLE";
    public static final String ERROR_MICROBLINK_SERVICE_INVALID_RESPONSE = "MICROBLINK_SERVICE_INVALID_RESPONSE";

    public static final String ERROR_MICROBLINK_IMAGE_IS_NOT_VALID = "IMAGE_IS_NOT_VALID";
    public static final String ERROR_MICROBLINK_IMAGE_IS_NOT_VALID_BASE64_STRING = "IMAGE_IS_NOT_VALID_BASE64_STRING";
    public static final String ERROR_MICROBLINK_BAD_REQUEST = "BAD_REQUEST";
    public static final String ERROR_MICROBLINK_SERVER_TOO_BUSY = "SERVER_TOO_BUSY";
    public static final String ERROR_MICROBLINK_INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    public static final String ERROR_MICROBLINK_AUTHORIZATION_HEADER_IS_NOT_VALID = "AUTHORIZATION_HEADER_IS_NOT_VALID";
}
