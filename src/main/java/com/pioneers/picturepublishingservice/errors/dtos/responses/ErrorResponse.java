package com.pioneers.picturepublishingservice.errors.dtos.responses;

/**
 * Represents a standardized error response returned by the API.
 *
 * @author esraa
 * @param message     A short summary of the error
 * @param description A detailed explanation of the error cause
 */
public record ErrorResponse(String message, String description) {
}
