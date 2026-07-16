package com.pioneers.picturepublishingservice.models.dtos.responses;

import lombok.Builder;

/**
 * A response DTO that provides the URL of a stored or accessible picture.
 *
 * @param url the direct URL pointing to the stored or accessible picture.
 */
@Builder
public record PictureUrlResponse(String url) {
}
