package com.pioneers.picturepublishingservice.models.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import com.pioneers.picturepublishingservice.models.enums.Role;

/**
 * Represents a sign-up request payload containing user registration details.
 *
 * @author esraa
 * @param email    The user's email address, validated with {@link jakarta.validation.constraints.Email}.
 * @param name     The user's full name, validated with {@link jakarta.validation.constraints.NotBlank}.
 * @param password The user's password, validated with {@link jakarta.validation.constraints.Pattern}.
 * @param role     The {@link com.pioneers.picturepublishingservice.models.enums.Role} assigned to the user
 *                 (e.g., USER, ADMIN). Determines the user's permissions in the system.
 */
public record UserSignup(
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Name is required")
        String name,
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
                message = "Password doesn't meet our criteria")
        String password,
        @NotBlank(message = "Role is required")
        Role role) {
}
