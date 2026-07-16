package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.UserNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Register issues.
 *
 * @author esraa
 */
@RestControllerAdvice
public class UserNotFoundExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final UserNotFoundException e) {
        final ErrorResponse error =
                new ErrorResponse(UserNotFoundException.USER_NOT_FOUND_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(UserNotFoundException.USER_NOT_FOUND_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}