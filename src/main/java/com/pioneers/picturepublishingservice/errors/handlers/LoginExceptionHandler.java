package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.LoginException;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles all exceptions required for Login issues.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class LoginExceptionHandler {

    /**
     * Handles {@link LoginException} thrown during user login operations.
     *
     * @param e the {@link LoginException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the login error code, timestamp, and detailed error response
     */
    @ExceptionHandler(LoginException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final LoginException e) {
        log.error("{} - {}", e.getMethodName(), e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(LoginException.LOGIN_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(LoginException.LOGIN_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
