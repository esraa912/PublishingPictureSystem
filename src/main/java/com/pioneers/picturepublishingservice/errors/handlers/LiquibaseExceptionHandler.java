package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.LiquibaseException;

import lombok.extern.slf4j.Slf4j;

import static com.pioneers.picturepublishingservice.utils.MethodNameExtractor.extractOriginalMethodName;

/**
 * Handles all exceptions required for Register issues.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class LiquibaseExceptionHandler {

    /**
     * Handles {@link LiquibaseException} thrown during Liquibase-related operations.
     *
     * @param e the {@link LiquibaseException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the error code, timestamp, and detailed error response
     */
    @ExceptionHandler(LiquibaseException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final LiquibaseException e) {
        String methodName = extractOriginalMethodName(e);
        log.error("{} - {}", methodName, e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(LiquibaseException.LIQUIBASE_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(LiquibaseException.LIQUIBASE_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}
