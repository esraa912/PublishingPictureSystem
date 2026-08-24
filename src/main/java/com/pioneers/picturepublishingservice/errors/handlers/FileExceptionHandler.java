package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.FileException;

import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler dedicated to managing {@link FileException} cases.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class FileExceptionHandler {

    /**
     * Handles {@link FileException} thrown during file operations.
     *
     * @param e the {@link FileException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the error code, timestamp, and detailed error response
     */
    @ExceptionHandler(FileException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final FileException e) {
        log.error("{} - {}", e.getMethodName(), e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(FileException.FILE_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(FileException.FILE_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
