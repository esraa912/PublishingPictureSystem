package com.pioneers.picturepublishingservice.utils;

import com.pioneers.picturepublishingservice.errors.exceptions.CredentialsException;
import lombok.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Utility class for the Credentials and tokens.
 */
// TODO: Create all unit tests for all methods in that class.
public final class CredentialsHelper {
    private CredentialsHelper() {
        throw new AssertionError("Utility class");
    }

    /**
     * Encode the password to a hashed value.
     *
     * @param password is the real plain text password.
     * @return the hashed password of the plain text password
     * @throws CredentialsException is the returned exception during the hashing process
     */
    public static String hashPassword(@NonNull final String password) throws CredentialsException {
        final byte[] hashed;
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            hashed = md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new CredentialsException("Cannot hash the password", e);
        }

        return Base64.getEncoder().encodeToString(hashed);
    }

    /**
     * Hash the source plain text password and verifies it with the target hashed sourcePassword.
     *
     * @param sourcePassword is the plain text source password.
     * @param hashedPassword is the hash value of the password.
     * @return true if the hashedPassword is equal to the hash value of the sourcePassword, false otherwise.
     * @throws CredentialsException is the thrown exception in the hashPassword method.
     */
    public static boolean verifyPassword(final String sourcePassword, final String hashedPassword)
            throws CredentialsException {
        final String hashedSourcePassword = hashPassword(sourcePassword);
        return hashedPassword.equals(hashedSourcePassword);
    }
}