package entity.task;

import java.sql.Timestamp;

public class TimeLog {
    private int id;
    private Task task;
    private Timestamp begin;
    private long length;
    private LogType logType;
}
