package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Picture Not Found issues.
 *
 */
@RestControllerAdvice
public class PictureNotFoundExceptionHandler {

    @ExceptionHandler(PictureNotFoundException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final PictureNotFoundException e) {
        final ErrorResponse error =
                new ErrorResponse(PictureNotFoundException.PICTURE_NOT_FOUND_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(PictureNotFoundException.PICTURE_NOT_FOUND_EXCEPTION_CODE,
                e.getTimestamp(), error);
    }
}