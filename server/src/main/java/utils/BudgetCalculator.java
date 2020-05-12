package utils;

import api.socket.UpdateUtil;
import entity.budget.Account;
import entity.budget.BudgetPoint;
import entity.budget.PointRoot;
import entity.budget.PointScale;
import entity.transactions.Transaction;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
public class BudgetCalculator {

    dbDAO dao = dbDAOService.getDao();
    private final UpdateUtil updateUtil = new UpdateUtil();

    public void calculatePointRoot(int parentId, Date date, Account account, float amount){
        PointRoot pointRoot = dao.getPointRoot(parentId, account);
        if (pointRoot == null){
            pointRoot = new PointRoot();
            pointRoot.setParentId(parentId);
        }

        Date otherDate = null;
        if(pointRoot.getDate() != null && !pointRoot.getDate().equals(date)){
            otherDate = pointRoot.getDate();
        }
        pointRoot.setDate(date);

        pointRoot.setAccount(account);

        pointRoot.setAmount(amount);
        dao.save(pointRoot);

        calculate(account, date);
        if (otherDate != null){
            calculate(account, otherDate);
        }
    }

    private void calculate(Account account, Date date) {
        BudgetPoint budgetPoint = dao.getBudgetPoint(account, date, PointScale.day);
        if (budgetPoint == null){
            budgetPoint = new BudgetPoint();
            budgetPoint.setDate(date);
            budgetPoint.setAccount(account);
            budgetPoint.setScale(PointScale.day);
        }
        float amount = 0;
        for (PointRoot root : dao.getPointRoots(account, date)){
            amount += root.getAmount();
        }
        budgetPoint.setQuantity(amount);
        dao.save(budgetPoint);

        calculatePointByPoint(date, account, PointScale.week);
        calculateBudget(account);
    }

    public void calculateBudget(Account account) {
        float amount = 0;
        for (BudgetPoint point : dao.getBudgetPoints(null, null, account,PointScale.year)){
            amount += point.getQuantity();
        }
        if (account.getBudgetSum() != amount) {
            account.setBudgetSum(amount);
            dao.save(account);
            try {
                updateUtil.onSave(account);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void calculatePointByPoint(Date date, Account account, PointScale scale) {
        LocalDate localDate = date.toLocalDate();
        LocalDate beginDate = getBeginDate(localDate, scale);
        LocalDate endDate = getEndDate(localDate, scale);

        BudgetPoint budgetPoint = dao.getBudgetPoint(account, Date.valueOf(beginDate), scale);
        if (budgetPoint == null){
            budgetPoint = new BudgetPoint();
            budgetPoint.setDate(Date.valueOf(beginDate));
            budgetPoint.setAccount(account);
            budgetPoint.setScale(scale);
        }
        float amount = 0;
        for (BudgetPoint point : dao.getBudgetPoints(Date.valueOf(beginDate), Date.valueOf(endDate), account, prevScale(scale))){
            amount += point.getQuantity();
        }
        budgetPoint.setQuantity(amount);
        dao.save(budgetPoint);
        PointScale next = nextScale(scale);
        if (next != scale){
            calculatePointByPoint(date, account, next);
        }
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

    public void calculatePointRoot(Transaction t) {
        if(t.getAccount() != null) {
            calculatePointRoot(t.getId(), t.getDate(), t.getAccount(), t.getTotalSum());
        }
        if (t.getSecondary() != null){
            calculatePointRoot(t.getId(), t.getDate(), t.getSecondary(), t.getTotalSum() * -1);
        }
    }

    public void removePointRoot(int parentId, Account account, Date date) {
        if (dao.removePointRoot(parentId, account, date)){
            calculate(account, date);
        }
    }
}
