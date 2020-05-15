package api.transactions.buy.list;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.transactions.buy.list.BuyList;
import entity.transactions.buy.list.BuyListItem;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(API.BUY_LIST_ITEM_EDIT)
public class BuyListItemEditAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            BuyListItem item =dao.getObjectById(BuyListItem.class, body.get(ID));
            if (item == null){
                item = new BuyListItem();
            }
            BuyList list = dao.getObjectById(BuyList.class, body.get(LIST));
            item.setBuyList(list);

            String title = String.valueOf(body.get(TITLE));
            item.setTitle(title);

            float price = Float.parseFloat(String.valueOf(body.get(PRICE)));
            item.setPrice(price);

            dao.save(item);
            write(resp, SUCCESS);

            updateUtil.onSave(list);

        }
    }
}
