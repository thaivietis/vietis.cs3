package com.nqt.cs3.conversion;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Service;

@Service
public class DateTimeFormat {
    public static LocalDateTime conversionInstantToLocalDateTime(Instant instant) {
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static Instant conversionLocalDateTimeToInstant(LocalDateTime dateTime) {
        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        return dateTime.atZone(zone).toInstant();
    }
    
}
