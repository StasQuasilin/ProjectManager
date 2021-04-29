package controllers.api.finance.buyList;

import constants.ApiLinks;
import controllers.api.API;
import entity.Title;
import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import entity.finance.category.TitleCost;
import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.Updater;
import utils.db.dao.TitleDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.finances.TitleCostUtil;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static constants.Keys.*;

@WebServlet(ApiLinks.BUY_LIST_EDIT)
public class EditBuyListAPI extends API {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();
    private final TitleDAO titleDAO = daoService.getTitleDAO();
    private final Updater updater = new Updater();
    private final TitleCostUtil titleCostUtil = new TitleCostUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            BuyList list = buyListDAO.getList(body.get(ID));
            final User user = getUser(req);
            Title title;
            if (list == null){
                list = new BuyList();
                list.setOwner(user);
                title = new Title();
                title.setOwner(user);
                title.setType(HeaderType.buy);
                list.setTitle(title);
            } else {
                title = list.getTitle();
            }
            if(title.getType() == HeaderType.buy) {
                title.setValue(body.getString(TITLE));
            }
            buyListDAO.saveList(list);

            final HashMap<Integer, BuyListItem> items = new HashMap<>();
            for (BuyListItem item : list.getItemSet()){
                items.put(item.getId(), item);
            }
            list.clearItems();
            final Header parent = titleDAO.getHeader(title.getId());
            float cost = 0;
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
                    header.setParent(parent);
                    header.setType(HeaderType.category);
                    remove.setHeader(header);
                }
                if(header.getType() == HeaderType.category) {
                    header.setValue(item.getString(TITLE));
                }
                remove.setCount(item.getFloat(COUNT));
                remove.setPrice(item.getFloat(PRICE));
                remove.setDate(item.getDate(DATE));
                cost += remove.getCost();
                list.addItem(remove);
            }
            buyListDAO.saveItems(list.getItemSet());

            final TitleCost titleCost = titleCostUtil.updateCost(list.getTitle(), cost);
            list.setCost(titleCost);
            updater.update(Subscribe.buy, list, list.getOwner());
            buyListDAO.removeItems(items.values());
            write(resp, SUCCESS_ANSWER);
        }
    }
}
