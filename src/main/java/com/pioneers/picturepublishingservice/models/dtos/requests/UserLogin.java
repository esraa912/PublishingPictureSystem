package com.pioneers.picturepublishingservice.models.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

/**
 * A request DTO representing user login credentials.
 *
 * @param email the user's email address (must be valid format).
 * @param password the user's password (must meet complexity requirements).
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
