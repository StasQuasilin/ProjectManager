package utils;

import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by ZPT_USER on 20.07.2018.
 */
public class DateParser {
    private static final Logger log = Logger.getLogger(DateParser.class);

    public static Date parse(String date) {
        Date value = parseToNull(date);
        if (value != null){
            return value;
        }
        return Date.valueOf(LocalDate.now());
    }
    static DateTimeFormatter f = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public static Time parseTime(String time){
        return Time.valueOf(LocalTime.from(f.parse(time)));
    }

    public static Date parseToNull(String date) {
        try {
            return new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime());
        } catch (Exception ignored) {}
        return null;
    }

    public static Date parseFromEditor(String dateFrom) {
        return parseFromEditor(dateFrom, "d.M.y");
    }

    public static Date parseFromEditor(String date, String format){
        try {
            return new Date(parseToLong(date, format));
        } catch (Exception e) {
            return Date.valueOf(LocalDate.now());
        }
    }

    public static long parseToLong(String date, String format) throws ParseException {
        return new SimpleDateFormat(format).parse(date).getTime();
    }

    public static String toString(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }
}
