package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

/**
 * Custom runtime exception used to indicate errors related to Liquibase rollback operations.
 *
 * @author esraa
 */
@Getter
public class LiquibaseRollbackException extends RuntimeException {
    public static final String LIQUIBASE_ROLLBACK_EXCEPTION_MESSAGE = "liquibaseRollbackException";
    public static final int LIQUIBASE_ROLLBACK_EXCEPTION_CODE = 1010;

    private final String description;
    private final Timestamp timestamp;

    public LiquibaseRollbackException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
