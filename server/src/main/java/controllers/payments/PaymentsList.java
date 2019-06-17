package controllers.payments;

import constants.API;
import constants.Links;
import controllers.IPage;
import entity.payments.PaymentType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 28.05.2019.
 */
@WebServlet(Links.PAYMENTS)
public class PaymentsList extends IPage {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("title", "title.payments");
        req.setAttribute("pageContent", "/pages/payments/paymentsList.jsp");
        req.setAttribute("paymentTypes", PaymentType.values());
        req.setAttribute("edit", Links.PAYMENTS_EDIT);
        req.setAttribute("update", API.Payments.UPDATE);
        showPage(req, resp);
    }
}
