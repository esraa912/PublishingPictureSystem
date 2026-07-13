package com.pioneers.picturepublishingservice.models.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
