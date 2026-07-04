package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.io.IOException;
import java.sql.Timestamp;

@Getter
public class PictureStorageException extends  RuntimeException {
    public static final String PICTURE_STORAGE_EXCEPTION_MESSAGE = "pictureStorageException";
    public static final int PICTURE_STORAGE_EXCEPTION_CODE = 1005;

    private final String description;
    private final IOException e;
    private final Timestamp timestamp = TimeHelper.currentTimestamp();

    public PictureStorageException(String description, IOException e) {
        super(description, e);
        this.description = description;
        this.e = e;
    }
}
