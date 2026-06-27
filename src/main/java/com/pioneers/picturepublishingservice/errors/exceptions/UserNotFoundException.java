package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class UserNotFoundException extends  RuntimeException {
    public static final String REGISTER_EXCEPTION_MESSAGE = "userNotFoundException";
    public static final int REGISTER_EXCEPTION_CODE = 1008;

    private final String description;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public UserNotFoundException(String description) {
        super(description);
        this.description = description;
    }
}
