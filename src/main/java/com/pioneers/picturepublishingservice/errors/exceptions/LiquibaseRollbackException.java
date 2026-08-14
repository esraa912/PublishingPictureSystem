package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

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

    /**
     * Constructs a new {@code LiquibaseRollbackException} with the specified description.
     *
     * @param description a detailed explanation of the Liquibase rollback error
     */
    public LiquibaseRollbackException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
