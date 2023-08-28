package ru.ashabelskii.conveyor.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.*;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeUtil {

    private static final TimeZone REAL_TIME_ZONE = TimeZone.getDefault();
    private static Clock clock = Clock.systemDefaultZone();

    public static LocalDate currentDate() {
        return LocalDate.now(clock);
    }

    public static LocalTime currentTime() {
        return LocalTime.now(clock);
    }

    public static LocalDateTime currentDateTime() {
        return LocalDateTime.now(clock);
    }

    public static OffsetDateTime currentOffsetDateTime() {
        return OffsetDateTime.now(clock);
    }

    public static ZonedDateTime currentZonedDateTime() {
        return ZonedDateTime.now(clock);
    }

    public static Instant currentInstant() {
        return Instant.now(clock);
    }

    public static long currentTimeMillis() {
        return currentInstant().toEpochMilli();
    }

    public static void useMockTime(LocalDateTime dateTime, ZoneId zoneId) {
        Instant instant = dateTime.atZone(zoneId).toInstant();
        clock = Clock.fixed(instant, zoneId);
        TimeZone.setDefault(TimeZone.getTimeZone(zoneId));
    }

    public static void useSystemDefaultZoneClock() {
        TimeZone.setDefault(REAL_TIME_ZONE);
        clock = Clock.systemDefaultZone();
    }
}
