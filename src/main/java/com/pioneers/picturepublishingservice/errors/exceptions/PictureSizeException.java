package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PictureSizeException extends  RuntimeException {
    public static final String PICTURE_SIZE_EXCEPTION_MESSAGE = "pictureSizeException";
    public static final int PICTURE_SIZE_EXCEPTION_CODE = 1006;

    private final String description;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public PictureSizeException(String description) {
        super(description);
        this.description = description;
    }
}
