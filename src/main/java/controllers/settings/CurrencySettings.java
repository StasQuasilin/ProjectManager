package controllers.settings;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.budget.Currency;
import entity.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(Branches.CURRENCY_SETTINGS)
public class CurrencySettings extends IModal {

    private static final String _TITLE = "title.currency.settings";
    private static final String _CONTENT = "/pages/settings/currencySettings.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(CURRENCY_EDIT, API.CURRENCY_EDIT);
        req.setAttribute(CURRENCY_REMOVE, API.CURRENCY_REMOVE);
        User user = getUser(req);
        req.setAttribute(USER_CURRENCY, dao.getUserCurrency(user));
        req.setAttribute(CURRENCY, dao.getObjects(Currency.class));
        showModal(req, resp);
    }
}
