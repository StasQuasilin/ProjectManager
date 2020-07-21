package utils;

import entity.calendar.CalendarItem;
import entity.calendar.ExecutionStatus;
import utils.db.hibernate.DateContainers.LT;
import utils.db.hibernate.Hibernator;
import utils.savers.CalendarSaver;

import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;

import static constants.Keys.DATE;
import static constants.Keys.STATUS;

public class CalendarReplaceUtil {

    private static CalendarReplaceUtil instance;
    public static void init(){
        instance = new CalendarReplaceUtil();
        instance.check(true);
    }

    public static void shutdown(){
        instance.stop();
    }

    private Timer timer;
    private void initTimer() {
        final LocalDateTime target = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(1, 0));
        final LocalDateTime now = LocalDateTime.now();
        long step = Math.twoDatesDifference(target, now);
        timer = new Timer((int)step, actionEvent -> check(false));
        timer.setRepeats(false);
        timer.start();
    }

    private final Hibernator hibernator = Hibernator.getInstance();
    private final CalendarSaver saver = new CalendarSaver();
    private void check(boolean repeat) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(DATE, new LT(Date.valueOf(LocalDate.now())));
        map.put(STATUS, ExecutionStatus.active);
        final Date date = Date.valueOf(LocalDate.now());
        CalendarItem item;
        while ((item = hibernator.get(CalendarItem.class, map)) != null){
            item.setDate(date);
            saver.save(item);
        }
        if (repeat) {
            initTimer();
        }
    }

    private void stop() {
        if (timer != null && timer.isRunning()){
            timer.stop();
        }
    }
}
