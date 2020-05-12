package utils;

import entity.budget.Budget;
import entity.budget.BudgetArticle;
import entity.budget.BudgetPoint;
import services.hibernate.DateContainers.LT;
import services.hibernate.Hibernator;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 27.05.2019.
 */
public class BudgetPointUtil {
    private static final Hibernator hibernator = Hibernator.getInstance();

    public static BudgetPoint getPoint(Budget budget, Timestamp timestamp){
        LocalDate date = timestamp.toLocalDateTime().toLocalDate();
        Date targetDate = Date.valueOf(LocalDate.of(date.getYear(), date.getMonth(), 1));
        BudgetPoint point = hibernator.get(BudgetPoint.class, "date", targetDate);
        if (point == null) {
            point = new BudgetPoint();
            point.setBudget(budget);
            point.setDate(targetDate);

            BudgetPoint prevPoint = hibernator.get(BudgetPoint.class, "date", new LT(targetDate));
            if (prevPoint != null) {
                float quantity = prevPoint.getQuantity();
                for (BudgetArticle article : hibernator.query(BudgetArticle.class, "point", prevPoint)){
                    quantity += article.getQuantity();
                }
                point.setQuantity(quantity);
            } else {
                point.setQuantity(budget.getBudgetSum());
            }
            hibernator.save(point);
        }
        return null;
    }

}
