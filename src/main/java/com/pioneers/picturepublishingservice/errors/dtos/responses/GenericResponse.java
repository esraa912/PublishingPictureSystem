package com.pioneers.picturepublishingservice.errors.dtos.responses;

import java.sql.Timestamp;

/**
 * A generic response wrapper used to standardize API responses across the system.
 *
 * @param code the status or error code associated with the response.
 * @param timestamp the timestamp indicating when the response was generated.
 * @param body the generic payload containing the response data.
 * @param <T> the type of the response body.
 */
public record GenericResponse<T>(int code, Timestamp timestamp, T body) {
}
