package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.RegisterException;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles all exceptions required for Register issues.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class RegisterExceptionHandler {

    /**
     * Handles {@link RegisterException} thrown during user registration operations.
     *
     * @param e the {@link RegisterException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the registration error code, timestamp, and detailed error response
     */
    @ExceptionHandler(RegisterException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final RegisterException e) {
        log.error("{} - {}", e.getMethodName(), e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(RegisterException.REGISTER_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(RegisterException.REGISTER_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
