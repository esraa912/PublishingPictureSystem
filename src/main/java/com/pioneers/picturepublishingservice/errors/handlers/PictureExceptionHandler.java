package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;

import lombok.extern.slf4j.Slf4j;

/**
 * Handles all exceptions required for Picture Extension issues.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class PictureExceptionHandler {

    /**
     * Handles {@link PictureException} thrown during picture-related operations.
     *
     * @param e the {@link PictureException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the picture error code, timestamp, and detailed error response
     */
    @ExceptionHandler(PictureException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final PictureException e) {
        log.error("{} - {}", e.getMethodName(), e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(PictureException.PICTURE_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(PictureException.PICTURE_EXCEPTION_CODE,
                e.getTimestamp(), error);
    }
}
