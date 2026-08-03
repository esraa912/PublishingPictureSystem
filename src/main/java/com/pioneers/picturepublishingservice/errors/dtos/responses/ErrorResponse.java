package com.pioneers.picturepublishingservice.errors.dtos.responses;

/**
 * Represents a standardized error response returned by the API.
 *
 * @param message     A short summary of the error
 * @param description A detailed explanation of the error cause
 * @author esraa
 */
public record ErrorResponse(String message, String description) {
}