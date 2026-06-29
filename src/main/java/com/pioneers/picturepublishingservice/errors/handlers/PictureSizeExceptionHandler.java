package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.PictureSizeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Picture size issues.
 *
 */
@RestControllerAdvice
public class PictureSizeExceptionHandler {

    @ExceptionHandler(PictureSizeException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final PictureSizeException e) {
        final ErrorResponse error =
                new ErrorResponse(PictureSizeException.PICTURE_SIZE_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(PictureSizeException.PICTURE_SIZE_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}