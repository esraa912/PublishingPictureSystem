package com.pioneers.picturepublishingservice.errors.dtos.responses;

/**
 * A standardized error response object used across the system.
 *
 * @param message the short summary of the error.
 * @param description the detailed explanation of the error.
 */
public record ErrorResponse(String message, String description) {
}