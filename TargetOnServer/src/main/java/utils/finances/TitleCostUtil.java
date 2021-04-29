package utils.finances;

import constants.Keys;
import entity.Title;
import entity.finance.category.TitleCost;
import entity.goal.Goal;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.hibernate.Hibernator;

public class TitleCostUtil {

    private final Hibernator hibernator = Hibernator.getInstance();
    private final GoalDAO goalDAO = daoService.getGoalDAO();

    public TitleCost updateCost(Title title, float cost) {
        TitleCost titleCost = hibernator.get(TitleCost.class, Keys.TITLE, title);
        if(titleCost == null){
            titleCost = new TitleCost();
            titleCost.setTitle(title);
        }
        titleCost.setTotalCost(cost);
        hibernator.save(titleCost);

        final Goal goal = goalDAO.getGoalByHeader(title.getId());
        if(goal != null){
            goal.setCost(titleCost);
            goalDAO.update(goal);
        }
        return titleCost;
    }
}
