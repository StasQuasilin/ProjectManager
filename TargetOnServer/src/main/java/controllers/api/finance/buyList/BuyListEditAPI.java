package controllers.api.finance.buyList;

import constants.ApiLinks;
import controllers.api.API;
import entity.Title;
import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Header;
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
import java.util.HashMap;

import static constants.Keys.*;

@WebServlet(ApiLinks.BUY_LIST_EDIT)
public class BuyListEditAPI extends API {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            BuyList list = buyListDAO.getList(body.get(ID));
            Title title;
            if (list == null){
                list = new BuyList();
                list.setOwner(getUser(req));
                title = new Title();
                list.setTitle(title);
            } else {
                title = list.getTitle();
            }

            title.setValue(body.getString(TITLE));

            final HashMap<Integer, BuyListItem> items = new HashMap<>();
            for (BuyListItem item : list.getItemSet()){
                items.put(item.getId(), item);
            }
            list.clearItems();
            final User user = getUser(req);
            for (Object o : (JSONArray)body.get(ITEMS)){
                JsonObject item = new JsonObject(o);
                BuyListItem remove = items.remove(item.getInt(ID));
                if (remove == null){
                    remove = new BuyListItem();
                    remove.setList(list);
                }
                Header header = remove.getHeader();
                if (header == null){
                    header = new Header();
                    header.setOwner(user);
                    remove.setHeader(header);
                }
                header.setValue(item.getString(TITLE));
                remove.setCount(item.getFloat(COUNT));
                remove.setPrice(item.getFloat(PRICE));
                remove.setDate(item.getDate(DATE));
                list.addItem(remove);
            }
            buyListDAO.saveList(list);
            buyListDAO.removeItems(items.values());
            write(resp, SUCCESS_ANSWER);
        }
    }
}
