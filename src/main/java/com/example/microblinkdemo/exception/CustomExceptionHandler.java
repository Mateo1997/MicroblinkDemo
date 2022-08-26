package com.example.microblinkdemo.exception;

import com.example.microblinkdemo.util.ResponseConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ExceptionInformation> handleBadRequestException(BadRequestException ex) {
        log.debug("BAD REQUEST EXCEPTION: {}", ex.getMessage());
        log.error("BAD REQUEST EXCEPTION", ex);
        return error(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ExceptionInformation> handleNotFoundException(NotFoundException ex) {
        log.error("NOT FOUND EXCEPTION: {}", ex.getMessage());
        log.debug("NOT FOUND EXCEPTION", ex);
        return error(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler({MethodNotAllowedException.class})
    public ResponseEntity<ExceptionInformation> handleMethodNotAllowedException(MethodNotAllowedException ex) {
        log.error("NOT FOUND EXCEPTION: {}", ex.getMessage());
        log.debug("NOT FOUND EXCEPTION", ex);
        return error(HttpStatus.METHOD_NOT_ALLOWED, ex);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInformation> handleRuntimeExceptions(Exception ex) {
        log.debug("EXCEPTION: {}", ex.getMessage());
        log.error("EXCEPTION", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionInformation(ResponseConstants.ERROR_INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<ExceptionInformation> error(HttpStatus status, Exception ex) {
        return ResponseEntity.status(status).body(new ExceptionInformation(ex.getMessage()));
    }

}
