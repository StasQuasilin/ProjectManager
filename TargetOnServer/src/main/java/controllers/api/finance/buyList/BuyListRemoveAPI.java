package controllers.api.finance.buyList;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.finance.buy.BuyList;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ApiLinks.BUY_LIST_REMOVE)
public class BuyListRemoveAPI extends API {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();
    private final Updater updater = new Updater();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            final BuyList list = buyListDAO.getList(body.get(Keys.ID));
            buyListDAO.removeList(list);
            updater.remove(Subscribe.buy, list.getId(), list.getOwner());
            write(resp, SUCCESS_ANSWER);
        }
    }
}
