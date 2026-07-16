package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.LogoutException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Logout issues.
 *
 * @author esraa
 */
@RestControllerAdvice
public class LogoutExceptionHandler {

    @ExceptionHandler(LogoutException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final LogoutException e) {
        final ErrorResponse error =
                new ErrorResponse(LogoutException.LOGOUT_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(LogoutException.LOGOUT_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}