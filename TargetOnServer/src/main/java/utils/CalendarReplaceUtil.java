package utils;

import entity.calendar.CalendarItem;
import entity.calendar.ExecutionStatus;
import entity.task.Task;
import entity.task.TaskStatus;
import utils.db.hibernate.DateContainers.LT;
import utils.db.hibernate.Hibernator;
import utils.savers.CalendarSaver;
import utils.savers.TaskSaver;

import javax.swing.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

import static constants.Keys.*;

public class CalendarReplaceUtil {

    private static CalendarReplaceUtil instance;
    public static void init(){
        instance = new CalendarReplaceUtil();
        instance.start();
    }

    public static void shutdown(){
        instance.stop();
    }
    private Timer timer;
    private void start() {
        timer = new Timer(-1, actionEvent -> check());
    }
    private final Hibernator hibernator = Hibernator.getInstance();
    private final CalendarSaver saver = new CalendarSaver();
    private void check() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(DATE, new LT(Date.valueOf(LocalDate.now())));
        map.put(STATUS, ExecutionStatus.active);
        final Date date = Date.valueOf(LocalDate.now());
        CalendarItem item;
        while ((item = hibernator.get(CalendarItem.class, map)) != null){
            item.setDate(date);
            saver.save(item);
        }
    }

    private void stop() {
        if (timer != null && timer.isRunning()){
            timer.stop();
        }
    }
}
