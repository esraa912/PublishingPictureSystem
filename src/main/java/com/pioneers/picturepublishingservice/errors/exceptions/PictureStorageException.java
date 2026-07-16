package com.pioneers.picturepublishingservice.errors.exceptions;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;
import lombok.Getter;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * Custom runtime exception used to represent errors that occur during picture storage operations.
 *
 * @author esraa
 */
@Getter
public class PictureStorageException extends  RuntimeException {
    public static final String PICTURE_STORAGE_EXCEPTION_MESSAGE = "pictureStorageException";
    public static final int PICTURE_STORAGE_EXCEPTION_CODE = 1005;

    private final IOException e;
    private final String description;
    private final Timestamp timestamp;

    public PictureStorageException(String description, IOException e) {
        super(description, e);
        this.e = e;
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
