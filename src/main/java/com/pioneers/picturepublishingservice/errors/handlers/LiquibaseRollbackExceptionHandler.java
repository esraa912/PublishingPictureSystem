package com.pioneers.picturepublishingservice.errors.handlers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.LiquibaseRollbackException;

import lombok.extern.slf4j.Slf4j;

import static com.pioneers.picturepublishingservice.utils.MethodNameExtractor.extractOriginalMethodName;

/**
 * Handles all exceptions required for Liquibase Rollback issues.
 *
 * @author esraa
 */
@Slf4j
@RestControllerAdvice
public class LiquibaseRollbackExceptionHandler {

    /**
     * Handles {@link LiquibaseRollbackException} thrown during Liquibase rollback operations.
     *
     * @param e the {@link LiquibaseRollbackException} containing error description and timestamp
     * @return a {@link GenericResponse} containing the rollback error code, timestamp, and detailed error response
     */
    @ExceptionHandler(LiquibaseRollbackException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final LiquibaseRollbackException e) {
        String methodName = extractOriginalMethodName(e);
        log.error("{} - {}", methodName, e.getMessage());

        final ErrorResponse error =
                new ErrorResponse(LiquibaseRollbackException.LIQUIBASE_ROLLBACK_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(LiquibaseRollbackException.LIQUIBASE_ROLLBACK_EXCEPTION_CODE,
                e.getTimestamp(), error
        );
    }
}
