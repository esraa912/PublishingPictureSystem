package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate login-related errors during the authentication process.
 *
 * @author esraa
 */
@Getter
public class LoginException extends RuntimeException {
    public static final String LOGIN_EXCEPTION_MESSAGE = "loginException";
    public static final int LOGIN_EXCEPTION_CODE = 1002;

    private final String description;
    private final String methodName;
    private final Timestamp timestamp;

    /**
     * Constructs a new {@code LoginException} with the specified description.
     *
     * @param description a detailed explanation of the Login error
     * @param methodName  the name of the method where the exception happen
     */
    public LoginException(String description, String methodName) {
        super(description);
        this.description = description;
        this.methodName = methodName;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
