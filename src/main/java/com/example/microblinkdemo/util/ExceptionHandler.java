package com.example.microblinkdemo.util;

import com.example.microblinkdemo.exception.BadRequestException;
import com.example.microblinkdemo.exception.InternalServerError;
import com.example.microblinkdemo.exception.ServiceUnavailableException;
import com.example.microblinkdemo.exception.UnauthorizedException;

import static com.example.microblinkdemo.util.ResponseConstants.*;

public class ExceptionHandler {

    public static void handleException(Exception e) {
            switch (e.getMessage()) {
                case ERROR_MICROBLINK_BAD_REQUEST, ERROR_MICROBLINK_IMAGE_IS_NOT_VALID,
                        ERROR_MICROBLINK_IMAGE_IS_NOT_VALID_BASE64_STRING -> throw new BadRequestException(e.getMessage());
                case ERROR_MICROBLINK_AUTHORIZATION_HEADER_IS_NOT_VALID -> throw new UnauthorizedException(e.getMessage());
                case ERROR_MICROBLINK_SERVICE_INVALID_RESPONSE, ERROR_MICROBLINK_INTERNAL_SERVER_ERROR -> throw new InternalServerError(e.getMessage());
                case ERROR_MICROBLINK_SERVER_TOO_BUSY -> throw new ServiceUnavailableException(e.getMessage());
                default -> throw new InternalServerError(ERROR_INTERNAL_SERVER_ERROR);
            }
    }
}
