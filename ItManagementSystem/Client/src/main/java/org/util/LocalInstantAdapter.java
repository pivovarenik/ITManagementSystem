package org.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LocalInstantAdapter
{
    public static String format(Instant time) {
        ZonedDateTime zonedDateTime = time.atZone(ZoneId.systemDefault());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");

        return zonedDateTime.format(formatter);
    }
}
