package com.pioneers.picturepublishingservice.utils.mappers;

import com.pioneers.picturepublishingservice.models.dtos.requests.PictureRequest;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

import static com.pioneers.picturepublishingservice.utils.time.TimeHelper.currentTimestamp;

/**
 * Mapper class to transform to/from Picture.
 */
@Slf4j
public class PictureMapper {

    private PictureMapper(){
        throw new AssertionError("Cannot instantiate the UserMapper");
    }

    /**
     * Transfer the PictureRequest to a Picture object.
     *
     * @param pictureRequest is the target request need to transform from it.
     * @return a new picture object from the request.
     */
    public static Picture toPicture(final PictureRequest pictureRequest){

        final String methodName = "toPicture()";
        final Timestamp currentTime = currentTimestamp();

        Picture picture = Picture.builder()
                .description(pictureRequest.description())
                .category(pictureRequest.category())
                .uploadedAt(currentTime)
                .build();

        log.debug("{}, Mapped to new Picture with id: [{}]", methodName, picture.getId());
        return picture;
    }

    /**
     * Transfer the Picture to a PictureResponse object.
     *
     * @param picture is the target request need to transform from it.
     * @return a new pictureResponse object from the request.
     */
    public static PictureResponse toPictureResponse(final Picture picture){

        return PictureResponse.builder()
                .description(picture.getDescription())
                .category(picture.getCategory())
                .width(picture.getWidth())
                .height(picture.getHeight())
                .build();
    }

    /**
     * Transfer the Picture to a PictureUrlResponse object.
     *
     * @param picture is the target request need to transform from it.
     * @return a new pictureUrlResponse object from the request.
     */
    public static PictureUrlResponse toPictureUrlResponse(final Picture picture){

        return PictureUrlResponse.builder()
                .url(picture.getUrl())
                .build();
    }
}
