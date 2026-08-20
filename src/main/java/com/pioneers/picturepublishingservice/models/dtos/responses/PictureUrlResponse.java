package com.pioneers.picturepublishingservice.models.dtos.responses;

/**
 * Represents a response payload containing the URL of a stored or accessible picture.
 *
 * @author esraa
 * @param url The direct URL pointing to the stored picture resource.
 */
public record PictureUrlResponse(String url) {
}
