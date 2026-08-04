package com.pioneers.picturepublishingservice.utils.time;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Utility class providing helper methods for working with time and timestamps.
 *
 * @author esraa
 */
public final class TimeHelper {

    private TimeHelper() {
        throw new AssertionError("Utility class");
    }

    public static Timestamp currentTimestamp() {
        return Timestamp.from(Instant.now());
    }
}