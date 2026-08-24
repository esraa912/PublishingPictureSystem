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
    public static final int LIQUIBASE_EXCEPTION_CODE = 1011;

    private final String description;
    private final String methodName;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code LiquibaseException} with the specified description.
     *
     * @param description a detailed explanation of the Liquibase error
     * @param methodName  the name of the method where the exception happen
     */
    public LiquibaseException(String description, String methodName) {
        super(description);
        this.description = description;
        this.methodName = methodName;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
