package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.LiquibaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Register issues.
 *
 */
@RestControllerAdvice
public class LiquibaseExceptionHandler {

    @ExceptionHandler(LiquibaseException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final LiquibaseException e) {
        final ErrorResponse error =
                new ErrorResponse(LiquibaseException.LIQUIBASE_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(LiquibaseException.LIQUIBASE_EXCEPTION_CODE, e.getTimestamp(), error);
    }
}