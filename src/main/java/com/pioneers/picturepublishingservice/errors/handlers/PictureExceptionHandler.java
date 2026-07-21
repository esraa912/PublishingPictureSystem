package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureException;

/**
 * Handles all exceptions required for Picture Extension issues.
 *
 * @author esraa
 */
@RestControllerAdvice
public class PictureExceptionHandler {

    @ExceptionHandler(PictureException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final PictureException e) {
        final ErrorResponse error =
                new ErrorResponse(PictureException.PICTURE_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(PictureException.PICTURE_EXCEPTION_CODE,
                e.getTimestamp(), error);
    }
}
