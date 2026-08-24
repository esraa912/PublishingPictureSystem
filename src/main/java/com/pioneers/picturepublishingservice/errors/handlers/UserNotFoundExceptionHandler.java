package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles all exceptions required for Register issues.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class UserNotFoundExceptionHandler {

    /**
     * Handles {@link UserNotFoundException} thrown when an operation
     * fails because the user could not be found in the system.
     *
     * @param e the {@link UserNotFoundException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the user-not-found error code,
     * timestamp, and detailed error response
     */
    @ExceptionHandler(UserNotFoundException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final UserNotFoundException e) {
        log.error("{} - {}", e.getMethodName(), e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(UserNotFoundException.USER_NOT_FOUND_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(UserNotFoundException.USER_NOT_FOUND_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
