package com.pioneers.picturepublishingservice.utils.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pioneers.picturepublishingservice.models.dtos.responses.PictureResponse;
import com.pioneers.picturepublishingservice.models.dtos.responses.PictureUrlResponse;
import com.pioneers.picturepublishingservice.models.entities.Picture;
import com.pioneers.picturepublishingservice.models.enums.Category;

/**
 * Unit tests for {@link PictureMapper}.
 *
 * @author esraa
 */
@ExtendWith(MockitoExtension.class)
public class PictureMapperTest {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    @Test
    void testToPictureResponseWhenPictureIsValidThenReturnCorrectResponse() {
        //Arrange
        final Picture picture = Picture.builder()
                .description("Sunset")
                .category(Category.NATURE)
                .width(DEFAULT_WIDTH)
                .height(DEFAULT_HEIGHT)
                .build();

        //Ack
        PictureResponse response = PictureMapper.toPictureResponse(picture);

        //Assert
        assertEquals("Sunset", response.description());
        assertEquals(Category.NATURE, response.category());
        assertEquals(DEFAULT_WIDTH, response.width());
        assertEquals(DEFAULT_HEIGHT, response.height());
    }

    @Test
    void testToPictureUrlResponseWhenPictureIsValidThenReturnCorrectResponse() {
        //Arrange
        final Picture picture = Picture.builder().url("upload/img.png").build();

        //Ack
        PictureUrlResponse response = PictureMapper.toPictureUrlResponse(picture);

        //Assert
        assertEquals("upload/img.png", response.url());
    }
}
