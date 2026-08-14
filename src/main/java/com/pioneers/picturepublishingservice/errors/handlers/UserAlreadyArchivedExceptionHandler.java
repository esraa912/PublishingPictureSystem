package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.UserAlreadyArchivedException;

/**
 * Handles all exceptions required for Register issues.
 *
 * @author esraa
 */
@RestControllerAdvice
public class UserAlreadyArchivedExceptionHandler {

    /**
     * Handles {@link UserAlreadyArchivedException} thrown when an operation
     * is attempted on a user that has already been archived in the system.
     *
     * @param e the {@link UserAlreadyArchivedException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the archival error code, timestamp, and detailed error response
     */
    @ExceptionHandler(UserAlreadyArchivedException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final UserAlreadyArchivedException e) {
        final ErrorResponse error =
                new ErrorResponse(UserAlreadyArchivedException.USER_ALREADY_ARCHIVED_EXCEPTION_MESSAGE,
                        e.getDescription());

        return new GenericResponse<>(UserAlreadyArchivedException.USER_ALREADY_ARCHIVED_EXCEPTION_CODE,
                e.getTimestamp(), error);
    }
}
