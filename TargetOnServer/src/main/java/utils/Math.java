package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Math {
    static final ZoneId ZONE_ID = ZoneId.systemDefault();
    public static long twoDatesDifference(LocalDateTime d1, LocalDateTime d2){
        return d1.atZone(ZONE_ID).toInstant().toEpochMilli() - d2.atZone(ZONE_ID).toInstant().toEpochMilli();
    }
}
