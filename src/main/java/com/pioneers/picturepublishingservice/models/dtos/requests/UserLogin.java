package com.pioneers.picturepublishingservice.models.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import lombok.Builder;

/**
 * Represents a login request payload containing user credentials.
 *
 * @author esraa
 * @param email    The user's email address, validated with {@link jakarta.validation.constraints.Email}.
 * @param password The user's password, validated with {@link jakarta.validation.constraints.Pattern}.
 */
@Builder
public record UserLogin(
        @Email(message = "Email must be valid")
        String email,
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
                message = "Password doesn't meet our criteria")
        String password) {
}
