package com.pioneers.picturepublishingservice.utils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit tests for the {@link StringUtils} utility class.
 *
 * @author esraa
 */
@ExtendWith(MockitoExtension.class)
public class StringUtilsTest {

    @Test
    void testIsNullOrBlankWhenNullThenReturnTrue() {
        // Assert
        assertTrue(StringUtils.isNullOrBlank(null));
    }

    @Test
    void testIsNullOrBlankWhenEmptyStringThenReturnTrue() {
        // Assert
        assertTrue(StringUtils.isNullOrBlank(""));
    }

    @Test
    void testIsNullOrBlankWhenBlankStringThenReturnTrue() {
        // Assert
        assertTrue(StringUtils.isNullOrBlank("   "));
    }

    @Test
    void testIsNullOrBlankWhenNonEmptyStringThenReturnFalse() {
        // Assert
        assertFalse(StringUtils.isNullOrBlank("file.txt"));
    }
}
