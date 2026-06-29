package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class UserAlreadyArchivedException extends  RuntimeException {
    public static final String USER_ALREADY_ARCHIVED_EXCEPTION_MESSAGE = "userAlreadyArchivedException";
    public static final int USER_ALREADY_ARCHIVED_EXCEPTION_CODE = 1008;

    private final String description;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public UserAlreadyArchivedException(String description) {
        super(description);
        this.description = description;
    }
}
