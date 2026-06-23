package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PictureNotFoundException extends  RuntimeException {
    public static final String REGISTER_EXCEPTION_MESSAGE = "pictureNotFoundException";
    public static final int REGISTER_EXCEPTION_CODE = 1004;

    private final String description;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public PictureNotFoundException(String description) {
        super(description);
        this.description = description;
    }
}
