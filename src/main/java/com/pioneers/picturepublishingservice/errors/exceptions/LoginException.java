package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class LoginException extends  RuntimeException {
    public static final String LOGIN_EXCEPTION_MESSAGE = "loginException";
    public static final int LOGIN_EXCEPTION_CODE = 1002;

    private final String description;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public LoginException(String description) {
        super(description);
        this.description = description;
    }
}
