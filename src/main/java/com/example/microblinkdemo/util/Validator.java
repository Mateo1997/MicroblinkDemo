package com.example.microblinkdemo.util;

import com.example.microblinkdemo.exception.BadRequestException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class Validator {

    private Validator() {
        throw new IllegalStateException("Validator class");
    }

    public static void request(BindingResult result) {
        if (result.hasFieldErrors()) {
            List<FieldError> errors = result.getFieldErrors();
            throw new BadRequestException(ResponseConstants.ERROR_MISSING_REQUEST_PARAMETER + errorsToString(errors));
        }
    }

    private static String errorsToString(List<FieldError> errors) {
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (FieldError error : errors) {
            sb.append(delimiter).append(error.getField()).append(" ").append(error.getDefaultMessage());
            delimiter = ", ";
        }
        return " (" + sb + ")";
    }
}
