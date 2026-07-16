package com.pioneers.picturepublishingservice.models.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * A request DTO representing user signup (registration) data.
 *
 * @param email the user's email address (must be valid format).
 * @param name the user's full name (must not be blank).
 * @param password the user's password (must meet complexity requirements).
 */
public record UserSignup(
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Name is required")
        String name,
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).{8,}$",
                message = "Password doesn't meet our criteria")
        String password)
{
}
