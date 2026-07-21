package com.pioneers.picturepublishingservice.errors.dtos.responses;

import java.sql.Timestamp;

/**
 * Represents a generic API response wrapper.
 *
 * @param code      The status code indicating success or failure of the request
 * @param timestamp The timestamp when the response was created
 * @param body      The response payload of type {@code T}
 * @param <T>       The type of the response body
 * @author esraa
 */
public record GenericResponse<T>(int code, Timestamp timestamp, T body) {
}
