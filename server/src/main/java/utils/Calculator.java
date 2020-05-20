package utils;

import entity.accounts.AccountPoint;
import entity.accounts.PointRoot;
import entity.accounts.PointScale;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.sql.Date;
import java.time.LocalDate;

public abstract class Calculator {

    dbDAO dao = dbDAOService.getDao();

    public final void calculatePointRoot(int parentId, Date date, int account, float amount){
        PointRoot pointRoot = dao.getPointRoot(parentId, account);
        Date otherDate = null;
        if (pointRoot == null){
            pointRoot = new PointRoot();
            pointRoot.setParentId(parentId);
        } else if (!pointRoot.getDate().equals(date)){
            otherDate = pointRoot.getDate();
        }

        pointRoot.setDate(date);
        pointRoot.setAccountId(account);
        pointRoot.setAmount(amount);
        dao.save(pointRoot);

        calculate(account, date);
        if (otherDate != null){
            calculate(account, otherDate);
        }
    }

    private void calculate(int accountId, Date date) {
        AccountPoint accountPoint = dao.getAccountPoint(accountId, date, PointScale.day);
        if (accountPoint == null){
            accountPoint = new AccountPoint();
            accountPoint.setDate(date);
            accountPoint.setAccount(accountId);
            accountPoint.setScale(PointScale.day);
        }
        float amount = 0;
        for (PointRoot root : dao.getPointRoots(accountId, date)){
            amount += root.getAmount();
        }
        accountPoint.setQuantity(amount);
        dao.save(accountPoint);

        calculatePointByPoint(date, accountId, PointScale.week);
        afterBurn(accountId);
    }

    public void afterBurn(int accountId){}

    private void calculatePointByPoint(Date date, int account, PointScale scale) {
        LocalDate localDate = date.toLocalDate();
        LocalDate beginDate = getBeginDate(localDate, scale);
        LocalDate endDate = getEndDate(localDate, scale);

        AccountPoint accountPoint = dao.getAccountPoint(account, Date.valueOf(beginDate), scale);
        if (accountPoint == null){
            accountPoint = new AccountPoint();
            accountPoint.setDate(Date.valueOf(beginDate));
            accountPoint.setAccount(account);
            accountPoint.setScale(scale);
        }
        float amount = 0;
        for (AccountPoint point : dao.getAccountPoints(Date.valueOf(beginDate), Date.valueOf(endDate), account, prevScale(scale))){
            amount += point.getQuantity();
        }
        accountPoint.setQuantity(amount);
        dao.save(accountPoint);
        PointScale next = nextScale(scale);
        if (next != scale){
            calculatePointByPoint(date, account, next);
        }
    }

    public static LocalDate getBeginDate(LocalDate date, PointScale scale){
        int minus;
        if (scale == PointScale.week) {
            minus = Math.min(toBegin(date, scale), toBegin(date, PointScale.month));
        } else {
            minus = toBegin(date, scale);
        }

        return date.minusDays(minus);
    }

    public static LocalDate getEndDate(LocalDate date, PointScale scale){
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

    public final void removePointRoot(int parentId, int account, Date date) {
        if (dao.removePointRoot(parentId, account, date)){
            calculate(account, date);
        }
    }
}
