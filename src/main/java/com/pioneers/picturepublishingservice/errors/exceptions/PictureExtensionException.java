package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PictureExtensionException extends  RuntimeException {
    public static final String PICTURE_EXTENSION_EXCEPTION_MESSAGE = "pictureExtensionException";
    public static final int PICTURE_EXTENSION_EXCEPTION_CODE = 1004;

    private final String description;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public PictureExtensionException(String description) {
        super(description);
        this.description = description;
    }
}
