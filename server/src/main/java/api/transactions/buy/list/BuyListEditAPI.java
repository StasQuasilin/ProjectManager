package api.transactions.buy.list;

import api.socket.UpdateUtil;
import constants.API;
import constants.Keys;
import controllers.ServletAPI;
import entity.transactions.buy.list.BuyList;
import entity.user.User;
import org.json.simple.JSONObject;
import services.answers.SuccessAnswer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(API.BUY_LIST_EDIT)
public class BuyListEditAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            BuyList buyList = dao.getObjectById(BuyList.class, body.get(ID));
            User user = getUser(req);

            if(buyList == null){
                buyList = new BuyList();
                buyList.setOwner(user);
            }

            String title = String.valueOf(body.get(TITLE));
            buyList.setTitle(title);

            dao.save(buyList);
            write(resp, SUCCESS);
            updateUtil.onSave(buyList);
        }
    }
}
