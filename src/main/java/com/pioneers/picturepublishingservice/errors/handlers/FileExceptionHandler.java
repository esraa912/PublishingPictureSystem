package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.utils.file.FileHelper;

/**
 * Handles all exceptions required for file deletion issues.
 *
 * @author esraa
 */
@RestControllerAdvice
public class FileExceptionHandler {

    /**
     * Handles {@link FileHelper.PictureException} thrown during file-related operations.
     *
     * @param e the {@link FileHelper.PictureException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the error code, timestamp, and detailed error response
     */
    @ExceptionHandler(FileHelper.PictureException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final FileHelper.PictureException e) {
        final ErrorResponse error =
                new ErrorResponse(FileHelper.PictureException.PICTURE_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(FileHelper.PictureException.PICTURE_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
