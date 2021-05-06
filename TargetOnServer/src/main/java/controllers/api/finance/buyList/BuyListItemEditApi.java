package controllers.api.finance.buyList;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Header;
import entity.finance.category.HeaderType;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(ApiLinks.SAVE_BUY_LIST_ITEM)
public class BuyListItemEditApi extends API {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            BuyListItem item = buyListDAO.getItem(body.get(ID));
            if (item == null){
                item = new BuyListItem();
                final Header header = new Header();
                header.setOwner(getUser(req));
                header.setType(HeaderType.buy);
                item.setHeader(header);
                final BuyList list = buyListDAO.getList(body.get(LIST));
                item.setList(list);
            }

            final Header header = item.getHeader();
            final String title = body.getString(TITLE);
            header.setValue(title);

            final float count = body.getFloat(COUNT);
            item.setCount(count);

            final float price = body.getFloat(PRICE);
            item.setPrice(price);

            buyListDAO.saveItem(item);
            Answer answer = new SuccessAnswer();
            answer.addAttribute(RESULT, item.getId());
            write(resp, answer);
        }
    }
}
