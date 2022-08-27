package com.example.microblinkdemo.util;

public class ResponseConstants {

    private ResponseConstants() {
        throw new IllegalStateException("ResponseConstants class");
    }

    public static final String ERROR_MISSING_REQUEST_PARAMETER = "Missing request parameter";
    public static final String ERROR_DUPLICATE_COPY_BOOKS_FOUND = "Duplicate copy book ids are found in collection";
    public static final String ERROR_USER_NOT_FOUND = "User not found";
    public static final String ERROR_BOOK_NOT_FOUND = "Book not found";
    public static final String ERROR_BOOK_ALREADY_LEND = "The book has been already lend";
    public static final String ERROR_INTERNAL_SERVER_ERROR = "Internal server error";
}
