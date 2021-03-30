package controllers.finances.buy.list;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.buy.BuyList;
import utils.answers.Answer;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(UrlLinks.BUY_LIST_REMOVE)
public class BuyListRemove extends ModalWindow {

    private static final String _TITLE = "title.buy.list.remove";
    private static final String _CONTENT = "/pages/finances/buyList/buyListRemove.jsp";
    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if(body != null){
            final BuyList list = buyListDAO.getList(body.get(Keys.ID));
            req.setAttribute(Keys.LIST, list);
            req.setAttribute(Keys.TITLE, _TITLE);
            req.setAttribute(Keys.CONTENT, _CONTENT);
            req.setAttribute(Keys.DELETE, ApiLinks.BUY_LIST_REMOVE);
            show(req, resp);
        }
    }
}
