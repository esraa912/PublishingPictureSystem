package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.LogoutException;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles all exceptions required for Logout issues.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class LogoutExceptionHandler {

    /**
     * Handles {@link LogoutException} thrown during user logout operations.
     *
     * @param e the {@link LogoutException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the logout error code, timestamp, and detailed error response
     */
    @ExceptionHandler(LogoutException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final LogoutException e) {
        log.error("{} - {}", e.getMethodName(), e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(LogoutException.LOGOUT_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(LogoutException.LOGOUT_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
