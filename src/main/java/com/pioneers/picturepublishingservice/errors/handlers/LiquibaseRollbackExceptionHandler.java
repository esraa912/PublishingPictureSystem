package com.pioneers.picturepublishingservice.errors.handlers;

import com.pioneers.picturepublishingservice.errors.dtos.responses.ErrorResponse;
import com.pioneers.picturepublishingservice.errors.dtos.responses.GenericResponse;
import com.pioneers.picturepublishingservice.errors.exceptions.LiquibaseRollbackException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all exceptions required for Register issues.
 *
 * @author esraa
 */
@RestControllerAdvice
public class LiquibaseRollbackExceptionHandler {

    @ExceptionHandler(LiquibaseRollbackException.class)
    public GenericResponse<ErrorResponse> handleNoSuchAlgorithmException(final LiquibaseRollbackException e) {
        final ErrorResponse error =
                new ErrorResponse(LiquibaseRollbackException.LIQUIBASE_ROLLBACK_EXCEPTION_MESSAGE, e.getDescription());

        return new GenericResponse<>(LiquibaseRollbackException.LIQUIBASE_ROLLBACK_EXCEPTION_CODE,
                e.getTimestamp(), error
        );
    }
}
