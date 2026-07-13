package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PictureException extends  RuntimeException {
    public static final String PICTURE_EXCEPTION_MESSAGE = "pictureException";
    public static final int PICTURE_EXCEPTION_CODE = 1004;

    private final String description;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public PictureException(String description) {
        super(description);
        this.description = description;
    }
}
