package controllers.finances;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.buy.BuyList;
import entity.task.Unit;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

/**
 * Created by DELL on 07.07.2020.
 */
@WebServlet(UrlLinks.BUY_LIST_EDIT)
public class BuyListEdit extends ModalWindow {
    private static final String _CONTENT = "/pages/finances/buyList/buyListEdit.jsp";
    private static final String _TITLE = "title.buy.list.edit";

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            final BuyList list = buyListDAO.getList(body.get(ID));
            req.setAttribute(LIST, list);
        }
        req.setAttribute(UNITS, Unit.values());
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SAVE, ApiLinks.BUY_LIST_EDIT);
        show(req, resp);
    }
}
