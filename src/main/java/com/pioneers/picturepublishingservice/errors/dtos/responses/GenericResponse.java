package com.pioneers.picturepublishingservice.errors.dtos.responses;

import java.sql.Timestamp;

public record GenericResponse<T>(int code, Timestamp timestamp, T body) {
}
