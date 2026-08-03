package com.pioneers.picturepublishingservice.errors.exceptions;

import java.sql.Timestamp;

import com.pioneers.picturepublishingservice.utils.time.TimeHelper;

import lombok.Getter;

/**
 * Custom runtime exception used to indicate errors related to picture operations,
 * such as uploading, processing, or retrieving images.
 *
 * @author esraa
 */
@Getter
public class PictureException extends RuntimeException {
    public static final String PICTURE_EXCEPTION_MESSAGE = "pictureException";
    public static final int PICTURE_EXCEPTION_CODE = 1004;

    private final String description;
    private final Timestamp timestamp;

    public PictureException(String description) {
        super(description);
        this.description = description;
        this.timestamp = TimeHelper.currentTimestamp();
    }
}
