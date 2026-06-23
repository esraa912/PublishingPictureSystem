package com.pioneers.picturepublishingservice.utils.mappers;

import com.pioneers.picturepublishingservice.models.dtos.requests.PictureRequest;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
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
                .id(UUID.randomUUID())
                .description(pictureRequest.description())
                .filePath(pictureRequest.filePath())
                .fileType(pictureRequest.fileType())
                .status(pictureRequest.status())
                .user(pictureRequest.user())
                .category(pictureRequest.category())
                .uploadedAt(currentTime)
                .width(pictureRequest.width())
                .height(pictureRequest.height())
                .build();

        log.debug("{}, Mapped to new Picture with id: [{}]", methodName, picture.getId());
        return picture;
    }

    public static PictureResponse toPictureResponse(final Picture picture){

        return PictureResponse.builder()
                .id(picture.getId())
                .description(picture.getDescription())
                .filePath(picture.getFilePath())
                .fileType(picture.getFileType())
                .status(picture.getStatus())
                .user(picture.getUser())
                .userEmail(picture.getUser().getEmail())
                .category(picture.getCategory())
                .createdAt(picture.getUploadedAt())
                .build();
    }
}
