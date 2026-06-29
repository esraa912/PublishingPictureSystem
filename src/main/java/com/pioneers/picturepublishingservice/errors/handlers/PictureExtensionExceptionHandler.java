package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureExtensionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Picture Extension issues.
 *
 */
@RestControllerAdvice
public class PictureExtensionExceptionHandler {

    @ExceptionHandler(PictureExtensionException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final PictureExtensionException e) {
        final ErrorResponse error =
                new ErrorResponse(PictureExtensionException.PICTURE_EXTENSION_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(PictureExtensionException.PICTURE_EXTENSION_EXCEPTION_CODE,
                e.getTimestamp(), error);
    }
}