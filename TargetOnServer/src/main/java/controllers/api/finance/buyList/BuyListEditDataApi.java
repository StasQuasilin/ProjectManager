package controllers.api.finance.buyList;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.buy.BuyList;
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

import static constants.Keys.ID;
import static constants.Keys.LIST;

@WebServlet(ApiLinks.BUY_LIST_EDIT + ApiLinks.DATA)
public class BuyListEditDataApi extends API {

    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer = new SuccessAnswer();
        if (body != null){
            final BuyList list = buyListDAO.getList(body.get(ID));
            answer.addAttribute(LIST, list.toJson());
        }
        write(resp, answer);
    }
}
