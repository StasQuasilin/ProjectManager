package controllers.api.finance.buyList;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.buy.BuyList;
import entity.finance.category.Category;
import entity.user.User;
import org.json.simple.JSONArray;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.KEY;

@WebServlet(ApiLinks.FIND_BUY_LIST)
public class FindBuyList extends API {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body !=null){
            final String key = body.getString(KEY);
            final User user = getUser(req);
            JSONArray array = new JSONArray();
            for (BuyList list : buyListDAO.findList(key, user)){
                array.add(list.shortJson());
            }
            write(resp, array.toJSONString());
        }
    }
}
