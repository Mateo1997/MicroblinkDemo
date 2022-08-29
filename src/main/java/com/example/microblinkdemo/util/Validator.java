package com.example.microblinkdemo.util;

import com.example.microblinkdemo.exception.BadRequestException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public static <T extends Number> void requestAndDuplicateBooks(BindingResult result, Collection<T> collection) {
        request(result);
        final Set<T> duplicates = findDuplicates(collection);
        if (!duplicates.isEmpty())
            throw new BadRequestException(ResponseConstants.ERROR_DUPLICATE_COPY_BOOKS_FOUND + setNumbersToString(duplicates));
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

    private static <T extends Number> String setNumbersToString(Set<T> numberSet) {
        StringBuilder sb = new StringBuilder();
        String delimiter = "";
        for (T number : numberSet) {
            sb.append(delimiter).append(number);
            delimiter = ", ";
        }
        return ": (" + sb + ")";
    }

    private static <T> Set<T> findDuplicates(Collection<T> collection) {
        Set<T> duplicates = new HashSet<>();
        Set<T> uniques = new HashSet<>();

        for(T t : collection) {
            if(!uniques.add(t)) {
                duplicates.add(t);
            }
        }

        return duplicates;
    }
}
