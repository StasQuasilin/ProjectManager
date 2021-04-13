package utils.finances;

import constants.Keys;
import entity.finance.accounts.AccountPoint;
import entity.finance.accounts.PointType;
import entity.finance.category.CategoryStatistic;
import entity.finance.category.Header;
import entity.finance.transactions.TransactionDetail;
import entity.finance.transactions.TransactionType;
import entity.task.TaskDependency;
import entity.task.TaskStatistic;
import entity.user.User;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

import java.sql.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static constants.Keys.HEADER;
import static constants.Keys.TRANSACTION_DATE;

public class CategoryPointUtil extends PointUtil<Header> {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();

    @Override
    public PointType getType() {
        return PointType.category;
    }

    @Override
    public int getAccountId(Header acc) {
        return acc.getId();
    }

    @Override
    public User getAccountOwner(Header acc) {
        return acc.getOwner();
    }

    @Override
    protected void updateAccount(Header acc, Date date) {

        final TaskStatistic statistic = taskDAO.getStatisticOrCreate(acc);
        statistic.clean();
        statistic.add(getTotalByYear(acc));
        if (statistic.any()){
            taskDAO.saveStatistic(statistic);
        } else {
            taskDAO.removeStatistic(statistic);
        }
        System.out.println(acc);
        final Header parent = acc.getParent();
//        System.out.println("Update parent " + parent);
        if(parent != null){
            updateAccount(parent, date);
        }
    }

    @Override
    public PlusMinus calculateAccountDay(Header acc, Date date) {
        final HashMap<String, Object> param = new HashMap<>();
        param.put(HEADER, acc);
        param.put(TRANSACTION_DATE, date);

        //Calculate header coast by transaction details
        PlusMinus plusMinus = new PlusMinus();
        for (TransactionDetail t : hibernator.query(TransactionDetail.class, param)) {
            float amount = t.getTotalPrice();
            if (t.getTransaction().getTransactionType() == TransactionType.spending ){
                amount = -amount;
            }
            if (amount > 0) {
                plusMinus.plus += amount;
            } else {
                plusMinus.minus += amount;
            }
        }
        addChildrenPoints(acc, date, plusMinus);
        return plusMinus;
    }

    private void addChildrenPoints(Header acc, Date date, PlusMinus plusMinus) {
        final HashMap<String,Object> args = new HashMap<>();
        args.put(Keys.DATE, date);
        LinkedList<AccountPoint> points = new LinkedList<>();
        for (Header header : hibernator.query(Header.class, Keys.PARENT, acc)){
            args.put(Keys.ACCOUNT, header.getId());
            points.addAll(hibernator.query(AccountPoint.class, args));
        }
        for(AccountPoint point : points){
            plusMinus.add(point);
        }
    }


    private void updateStatistic(Header header, PlusMinus plusMinus) {
        CategoryStatistic statistic = categoryDAO.getCategoryStatistic(header);
        if (plusMinus.any()) {
            if(statistic == null){
                statistic = new CategoryStatistic();
                statistic.setHeader(header);
            }
            statistic.setPlus(plusMinus.plus);
            statistic.setMinus(plusMinus.minus);
            hibernator.save(statistic);
            final Header parent = header.getParent();
            if (parent != null){
                updateParentStatistic(parent);
            }
        } else if (statistic != null){
            hibernator.remove(statistic);
        }
    }

    private void updateParentStatistic(Header parent) {
        PlusMinus plusMinus = new PlusMinus();
        HashMap<String, Object> args = new HashMap<>();
        for (AccountPoint accountPoint : hibernator.query(AccountPoint.class, args)){
            plusMinus.plus += accountPoint.getPlus();
            plusMinus.minus += accountPoint.getMinus();
        }

        updateStatistic(parent, plusMinus);
    }
}
