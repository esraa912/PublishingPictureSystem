package com.pioneers.picturepublishingservice.utils.time;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public final class TimeHelper {

    private TimeHelper() {
        throw new AssertionError();
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

    private static Long currentTimeMillis() {
        return new Date().getTime();
    }

    public static Timestamp timeSinceMinutesAgo(final int minutes) {
        return Timestamp.valueOf(LocalDateTime.now().minusMinutes(minutes));
    }

    public static LocalDateTime getLocalDateTimeBefore(final int minutes, String zoneId) {
        return LocalDateTime
                .now(ZoneId.of(zoneId))
                .minusMinutes(minutes);
    }
}