package entity.communication;

import entity.user.User;

import java.sql.Timestamp;

public class Notification {
    private int id;
    private Timestamp time;
    private User recipient;
    private String subject;
    private String message;
    private NotificationType type;
    private boolean viewed;
}
