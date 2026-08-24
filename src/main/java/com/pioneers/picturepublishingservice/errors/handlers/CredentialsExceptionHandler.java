package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.CredentialsException;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles all exceptions required for Credentials issues.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class CredentialsExceptionHandler {

    /**
     * Handles {@link CredentialsException} thrown during authentication or credential validation processes.
     *
     * @param e the {@link CredentialsException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the error code, timestamp, and detailed error response
     */
    @ExceptionHandler(CredentialsException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final CredentialsException e) {
        log.error("{} - {}", e.getMethodName(), e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(CredentialsException.CREDENTIALS_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(CredentialsException.CREDENTIALS_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
