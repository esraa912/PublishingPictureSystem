package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.UserAlreadyArchivedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Register issues.
 *
 * @author esraa
 */
@RestControllerAdvice
public class UserAlreadyArchivedExceptionHandler {

    @ExceptionHandler(UserAlreadyArchivedException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final UserAlreadyArchivedException e) {
        final ErrorResponse error =
                new ErrorResponse(UserAlreadyArchivedException.USER_ALREADY_ARCHIVED_EXCEPTION_MESSAGE,
                        e.getDescription());

        return new GenericResponse<>(UserAlreadyArchivedException.USER_ALREADY_ARCHIVED_EXCEPTION_CODE,
                e.getTimestamp(), error);
    }
}