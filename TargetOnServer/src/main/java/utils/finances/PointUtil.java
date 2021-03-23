package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountPoint;
import entity.finance.accounts.PointScale;
import entity.finance.accounts.PointType;
import entity.user.User;
import utils.db.hibernate.DateContainers.BETWEEN;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public abstract class PointUtil<T> {

    final Hibernator hibernator = Hibernator.getInstance();

    public abstract PointType getType();
    public abstract int getAccountId(T acc);
    public abstract User getAccountOwner(T acc);
    protected abstract PlusMinus calculateAccountDay(T acc, Date date);
    protected abstract void updateAccount(T acc, Date date);

    public void calculateDay(T acc, Date date){
        PlusMinus plusMinus = calculateAccountDay(acc, date);

        final int accountId = getAccountId(acc);
        final User owner = getAccountOwner(acc);

        AccountPoint point = getPointOrCreate(accountId, owner, date, PointScale.day);
        if (plusMinus.any()) {
            point.setPlus(plusMinus.plus);
            point.setMinus(plusMinus.minus);
            hibernator.save(point);
        } else if (point.getId() > 0){
            hibernator.remove(point);
        }

        pointByPoint(accountId, date, PointScale.week, owner);
        updateAccount(acc, date);
    }
    public void pointByPoint(int accountId, Date date, PointScale scale, User user) {
        final LocalDate localDate = date.toLocalDate();
        final LocalDate beginDate = getBeginDate(localDate, scale);
        final LocalDate endDate = getEndDate(localDate, scale);

        float plus = 0;
        float minus = 0;

        for (AccountPoint p : getYearPoints(accountId, Date.valueOf(beginDate), Date.valueOf(endDate), prevScale(scale))){
            plus += p.getPlus();
            minus += p.getMinus();
        }

        final AccountPoint point = getPointOrCreate(accountId, user, Date.valueOf(beginDate), scale);
        if(plus != 0 || minus != 0) {
            point.setPlus(plus);
            point.setMinus(minus);
            hibernator.save(point);
        } else if(point.getId() > 0){
            hibernator.remove(point);
        }

        PointScale next = nextScale(scale);
        if (next != scale){
            pointByPoint(accountId, date, next, user);
        }
    }

    private List<AccountPoint> getYearPoints(int account, Date from, Date to, PointScale scale){
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ACCOUNT, account);
        param.put(DATE, new BETWEEN(from, to));
        param.put(SCALE, scale);
        return hibernator.query(AccountPoint.class, param);
    }

    public AccountPoint getPointOrCreate(int account, User pointOwner, Date date, PointScale scale){
        AccountPoint point = getPoint(account, date, scale);
        if (point == null){
            point = new AccountPoint();
            point.setAccount(account);
            point.setType(getType());
            point.setOwner(pointOwner);
            point.setDate(date);
            point.setScale(scale);
        }
        return point;
    }

    private AccountPoint getPoint(int account, Date date, PointScale scale) {
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ACCOUNT, account);
        param.put(DATE, date);
        param.put(SCALE, scale);
        return hibernator.get(AccountPoint.class, param);
    }

    public static synchronized LocalDate getBeginDate(LocalDate date, PointScale scale){
        int minus;
        if (scale == PointScale.week) {
            minus = Math.min(toBegin(date, scale), toBegin(date, PointScale.month));
        } else {
            minus = toBegin(date, scale);
        }

        return date.minusDays(minus);
    }

    public static synchronized LocalDate getEndDate(LocalDate date, PointScale scale){
        int plus;
        if (scale == PointScale.week) {
            plus = Math.min(toEnd(date, scale), toEnd(date, PointScale.month));
        } else {
            plus = toEnd(date, scale);
        }
        return date.plusDays(plus);
    }

    public static int toBegin(LocalDate date, PointScale scale){
        switch (scale){
            case week:
                return date.getDayOfWeek().getValue() - 1;
            case month:
                return date.getDayOfMonth() - 1;
            case year:
                return date.getDayOfYear() - 1;
            default:
                return 0;
        }
    }

    public static int toEnd(LocalDate date, PointScale scale){
        switch (scale){
            case week:
                return 7 - date.getDayOfWeek().getValue();
            case month:
                return date.lengthOfMonth() - date.getDayOfMonth();
            case year:
                return date.lengthOfYear() - date.getDayOfYear();
            default:
                return 0;
        }
    }

    public PointScale prevScale(PointScale scale){
        switch (scale){
            case year:
                return PointScale.month;
            case month:
                return PointScale.week;
            case week:
                return PointScale.day;
            default:
                return scale;
        }
    }

    public static PointScale nextScale(PointScale scale){
        switch (scale){
            case day:
                return PointScale.week;
            case week:
                return PointScale.month;
            case month:
                return PointScale.year;
            default:
                return scale;
        }
    }



    private List<AccountPoint> getYearPoints(int account){
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ACCOUNT, account);
        param.put(SCALE, PointScale.year);
        return hibernator.query(AccountPoint.class, param);
    }

    public void removeDay(Date date, Account account) {
        final AccountPoint point = getPoint(account.getId(), date, PointScale.day);
        if (point != null){
            hibernator.remove(point);
        }
    }

    PlusMinus getTotalByYear(T acc) {
        PlusMinus plusMinus = new PlusMinus();
        for (AccountPoint point : getYearPoints(getAccountId(acc))){
            plusMinus.plus += point.getPlus();
            plusMinus.minus += point.getMinus();
        }
        return plusMinus;
    }
}
