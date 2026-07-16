package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.LoginException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Login issues.
 *
 * @author esraa
 */
@RestControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final LoginException e) {
        final ErrorResponse error =
                new ErrorResponse(LoginException.LOGIN_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(LoginException.LOGIN_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}