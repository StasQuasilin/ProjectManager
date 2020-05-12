package api.transactions.buy.list;

import constants.API;
import controllers.ServletAPI;
import entity.transactions.buy.list.BuyList;
import entity.user.User;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(API.BUY_LIST_EDIT)
public class BuyListEditAPI extends ServletAPI {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            BuyList buyList = dao.getObjectById(BuyList.class, body.get(ID));
            User user = getUser(req);
            if(buyList == null){
                buyList = new BuyList();
                buyList.setOwner(user);
            }
        }
    }
}
