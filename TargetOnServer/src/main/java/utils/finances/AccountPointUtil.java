package utils.finances;

import entity.finance.accounts.Account;
import entity.finance.accounts.AccountPoint;
import entity.finance.accounts.PointScale;
import utils.db.dao.daoService;
import utils.db.dao.finance.accounts.AccountDAO;
import utils.db.hibernate.DateContainers.BETWEEN;
import utils.db.hibernate.Hibernator;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static constants.Keys.*;

public class AccountPointUtil {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final AccountDAO accountDAO = daoService.getAccountDAO();

    public void pointByPoint(int accountId, Date date, PointScale scale) {
        final LocalDate localDate = date.toLocalDate();
        final LocalDate beginDate = getBeginDate(localDate, scale);
        final LocalDate endDate = getEndDate(localDate, scale);

        float plus = 0;
        float minus = 0;

        for (AccountPoint p : getPoints(accountId, Date.valueOf(beginDate), Date.valueOf(endDate), prevScale(scale))){
            plus += p.getPlus();
            minus += p.getMinus();
        }

        final AccountPoint point = getPoint(accountId, Date.valueOf(beginDate), scale);
        point.setPlus(plus);
        point.setMinus(minus);

        hibernator.save(point);

        PointScale next = nextScale(scale);
        if (next != scale){
            pointByPoint(accountId, date, next);
        }
    }

    private List<AccountPoint> getPoints(int account, Date from, Date to, PointScale scale){
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ACCOUNT, account);
        param.put(DATE, new BETWEEN(from, to));
        param.put(SCALE, scale);
        return hibernator.query(AccountPoint.class, param);
    }

    public AccountPoint getPoint(int account, Date date, PointScale scale){
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ACCOUNT, account);
        param.put(DATE, date);
        param.put(SCALE, scale);
        AccountPoint point = hibernator.get(AccountPoint.class, param);
        if (point == null){
            point = new AccountPoint();
            point.setAccount(account);
            point.setDate(date);
            point.setScale(scale);
        }
        return point;
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

    public void updateAccount(Account account) {
        float amount = 0;
        for (AccountPoint point : getPoints(account.getId())){
            amount += point.getPlus() + point.getMinus();
        }
        account.setSum(amount);
        accountDAO.saveAccount(account);
    }

    public List<AccountPoint> getPoints(int account){
        final HashMap<String, Object> param = new HashMap<>();
        param.put(ACCOUNT, account);
        param.put(SCALE, PointScale.year);
        return hibernator.query(AccountPoint.class, param);
    }
}
