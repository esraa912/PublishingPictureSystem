package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureStorageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Picture Extension issues.
 *
 */
@RestControllerAdvice
public class PictureStorageExceptionHandler {

    @ExceptionHandler(PictureStorageException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final PictureStorageException e) {
        final ErrorResponse error =
                new ErrorResponse(PictureStorageException.PICTURE_STORAGE_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(PictureStorageException.PICTURE_STORAGE_EXCEPTION_CODE,
                e.getTimestamp(), error);
    }
}