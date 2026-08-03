package com.pioneers.picturepublishingservice.models.dtos.responses;

import lombok.Builder;

/**
 * Represents a response payload containing the URL of a stored or accessible picture.
 *
 * @param url The direct URL pointing to the stored picture resource.
 * @author esraa
 */
@Builder
public record PictureUrlResponse(String url) {
}
