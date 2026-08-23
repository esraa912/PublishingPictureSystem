package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.InvalidUrlException;

import lombok.extern.slf4j.Slf4j;

import static com.pioneers.picturepublishingservice.utils.MethodNameExtractor.extractOriginalMethodName;

/**
 * Global exception handler dedicated to managing {@link InvalidUrlException} cases.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class InvalidUrlExceptionHandler {

    /**
     * Handles {@link InvalidUrlException} thrown during invalid or malformed URLs operations.
     *
     * @param e the {@link InvalidUrlException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the error code, timestamp, and detailed error response
     */
    @ExceptionHandler(InvalidUrlException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final InvalidUrlException e) {
        String methodName = extractOriginalMethodName(e);
        log.error("{} - {}", methodName, e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(InvalidUrlException.INVALID_URL_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(InvalidUrlException.INVALID_URL_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
