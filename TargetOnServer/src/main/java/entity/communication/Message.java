package entity.communication;

import entity.user.User;

import java.sql.Timestamp;

public class Message {
    private int id;
    private Timestamp time;
    private User sender;
    private User recipient;
    private String subject;
    private String message;
    private boolean open;

}
