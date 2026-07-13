package com.pioneers.picturepublishingservice.utils.mappers;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.CATEGORY;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PictureMapperTest {

    @Test
    void testToPictureResponse_WhenPictureIsValid_ThenReturnCorrectResponse() {
        //Arrange
        Picture picture = Picture.builder()
                .description("Sunset")
                .category(CATEGORY.NATURE)
                .width(800)
                .height(600)
                .build();

        //Ack
        PictureResponse response = PictureMapper.toPictureResponse(picture);

        //Assert
        assertEquals("Sunset", response.description());
        assertEquals(CATEGORY.NATURE, response.category());
        assertEquals(800, response.width());
        assertEquals(600, response.height());
    }

    @Test
    void testToPictureUrlResponse_WhenPictureIsValid_ThenReturnCorrectResponse() {
        //Arrange
        Picture picture = Picture.builder().url("upload/img.png").build();

        //Ack
        PictureUrlResponse response = PictureMapper.toPictureUrlResponse(picture);

        //Assert
        assertEquals("upload/img.png", response.url());
    }
}
