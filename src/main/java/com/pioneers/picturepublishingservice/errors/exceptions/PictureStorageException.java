package com.pioneers.picturepublishingservice.errors.exceptions;

import java.io.IOException;
import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate errors related to picture storage operations,
 * such as saving, reading, or writing image files.
 *
 * @author esraa
 */
@Getter
public class PictureStorageException extends RuntimeException {
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
