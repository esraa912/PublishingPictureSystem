package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate errors related to Liquibase migrations.
 *
 * @author esraa
 */
@Getter
public class LiquibaseException extends RuntimeException {
    public static final String LIQUIBASE_EXCEPTION_MESSAGE = "liquibaseException";
    public static final int LIQUIBASE_EXCEPTION_CODE = 1009;

    private final String description;
    private final Timestamp timestamp;

    public LiquibaseException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
