package controllers.finances;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;

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
    private static final String _CONTENT = "/pages/finances/buyListEdit.jsp";

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(SAVE, ApiLinks.BUY_LIST_SAVE);
        show(req, resp);
    }
}
