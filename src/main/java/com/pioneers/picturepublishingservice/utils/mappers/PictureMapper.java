package com.pioneers.picturepublishingservice.utils.mappers;

import com.pioneers.picturepublishingservice.models.dtos.requests.PictureRequest;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.PICTURE_STATUS;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.UUID;

import static com.pioneers.picturepublishingservice.utils.CredentialsHelper.hashPassword;
import static com.pioneers.picturepublishingservice.utils.time.TimeHelper.currentTimestamp;

@Slf4j
public class PictureMapper {

    private PictureMapper(){
        throw new AssertionError("Cannot instantiate the UserMapper");
    }

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

    public static PictureResponse toPictureResponse(final Picture picture){

        return PictureResponse.builder()
                .description(picture.getDescription())
                .category(picture.getCategory())
                .filePath(picture.getFilePath())
                .status(picture.getStatus())
                .userId(picture.getUser().getId())
                .userEmail(picture.getUser().getEmail())
                .build();
    }
}
