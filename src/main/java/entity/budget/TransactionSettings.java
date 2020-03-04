package entity.budget;

import entity.user.User;

import java.sql.Date;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
public class TransactionSettings {
    private int id;
    private Date beginDate;
    private Date finalDate;
    private TransactionRepeat repeat;
    private User owner;
    private Budget budget;
    private TransactionCategory category;
    private float amount;

}
