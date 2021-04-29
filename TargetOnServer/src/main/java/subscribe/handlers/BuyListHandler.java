package subscribe.handlers;

import entity.Title;
import entity.finance.buy.BuyList;
import entity.finance.category.TitleCost;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.db.hibernate.Hibernator;

import static constants.Keys.TITLE;

public class BuyListHandler extends SubscribeHandler {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();
    private final Hibernator hibernator = Hibernator.getInstance();

    public BuyListHandler() {
        super(Subscribe.buy);
    }

    @Override
    public Object getItems(User user) {
        JSONArray array = new JSONArray();
        for (BuyList list : buyListDAO.getUserList(user)){
            list.setCost(hibernator.get(TitleCost.class, TITLE, list.getTitle()));
            array.add(list.toJson());
        }
        return array;
    }
}
