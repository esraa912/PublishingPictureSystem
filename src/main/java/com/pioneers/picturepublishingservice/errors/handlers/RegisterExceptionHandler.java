package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.RegisterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Register issues.
 *
 */
@RestControllerAdvice
public class RegisterExceptionHandler {

    @ExceptionHandler(RegisterException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final RegisterException e) {
        final ErrorResponse error =
                new ErrorResponse(RegisterException.REGISTER_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(RegisterException.REGISTER_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}