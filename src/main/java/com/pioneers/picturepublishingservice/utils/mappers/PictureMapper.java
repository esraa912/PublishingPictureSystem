package com.pioneers.picturepublishingservice.utils.mappers;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;

import lombok.extern.slf4j.Slf4j;

/**
 * Mapper class to transform to/from Picture.
 *
 * @author esraa
 */
@Slf4j
public final class PictureMapper {

    private PictureMapper() {
        throw new AssertionError("Cannot instantiate the UserMapper");
    }

    /**
     * Transfer the Picture to a PictureResponse object.
     *
     * @param picture is the target request need to transform from it.
     * @return a new pictureResponse object from the request.
     */
    public static PictureResponse toPictureResponse(final Picture picture) {
        return PictureResponse.builder()
                .description(picture.getDescription())
                .category(picture.getCategory())
                .width(picture.getWidth())
                .height(picture.getHeight())
                .build();
    }

    /**
     * Creates a new {@link PictureUrlResponse} object from the given URL.
     *
     * @param url the direct URL pointing to the stored picture resource
     * @return a new {@link PictureUrlResponse} containing the given URL
     */
    public static PictureUrlResponse toPictureUrlResponse(final String url) {
        return new PictureUrlResponse(url);
    }
}
