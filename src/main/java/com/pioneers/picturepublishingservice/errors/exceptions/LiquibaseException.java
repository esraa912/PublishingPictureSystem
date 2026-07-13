package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class LiquibaseException extends  RuntimeException {
    public static final String LIQUIBASE_EXCEPTION_MESSAGE = "liquibaseException";
    public static final int LIQUIBASE_EXCEPTION_CODE = 1009;

    private final String description;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public LiquibaseException(String description) {
        super(description);
        this.description = description;
    }
}
