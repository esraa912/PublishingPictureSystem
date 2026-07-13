package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.CredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Credentials issues.
 *
 */
@RestControllerAdvice
public class CredentialsExceptionHandler {

    @ExceptionHandler(CredentialsException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final CredentialsException e) {
        final ErrorResponse error =
                new ErrorResponse(CredentialsException.CREDENTIALS_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(CredentialsException.CREDENTIALS_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}