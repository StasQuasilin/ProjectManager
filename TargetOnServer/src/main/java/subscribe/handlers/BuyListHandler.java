package subscribe.handlers;

import entity.finance.buy.BuyList;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;

public class BuyListHandler extends SubscribeHandler {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    public BuyListHandler() {
        super(Subscribe.buy);
    }

    @Override
    public Object getItems(User user) {
        JSONArray array = new JSONArray();
        for (BuyList list : buyListDAO.getUserList(user)){
            array.add(list.toJson());
        }
        return array;
    }
}
