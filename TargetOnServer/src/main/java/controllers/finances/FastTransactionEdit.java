package controllers.finances;

import constants.UrlLinks;
import controllers.ModalWindow;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.CONTENT;
import static constants.Keys.TITLE;

@WebServlet(UrlLinks.FAST_TRANSACTION_EDIT)
public class FastTransactionEdit extends ModalWindow {
    private static final String _TITLE = "title.fast.transaction.edit";
    private static final String _CONTENT = "";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);

        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        show(req, resp);
    }
}
