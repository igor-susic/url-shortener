package com.demo.urlshortener;

import com.demo.urlshortener.models.ApiErrorModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles specific exceptions that are often when sending wrong data to API endpoint
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ApiErrorModel error = new ApiErrorModel(
                HttpStatus.BAD_REQUEST,
                String.format("%s method is not allowed for %s", ex.getMethod(), request.getDescription(false)),
                ex
        );

        return new ResponseEntity<>(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiErrorModel error = new ApiErrorModel(
                HttpStatus.CONFLICT,
                buildErrorMessageForMethodArgumentNotValid(ex),
                ex
        );

        return new ResponseEntity<>(error, status);
    }

    private String buildErrorMessageForMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        var wrapper = new Object() {String message = "";};

        ex.getBindingResult().getFieldErrors().forEach((fieldError) -> {
            wrapper.message += String.format(
                    "Value {%s} rejected for field {%s}\n",
                    fieldError.getRejectedValue() == null ? "user_did_not_insert_any_value" : fieldError.getRejectedValue().toString(),
                    fieldError.getField()
            );
        });

        return wrapper.message;
    }
}
