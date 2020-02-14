package controllers.payments;

import constants.API;
import constants.Branches;
import controllers.IModal;
import entity.budget.Budget;
import entity.payments.PaymentType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 28.05.2019.
 */
@WebServlet(Branches.PAYMENTS_EDIT)
public class PaymentsEdit extends IModal {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("paymentTypes", PaymentType.values());
        req.setAttribute("type", req.getParameter("type"));
        req.setAttribute("budgets", hibernator.query(Budget.class, "owner", getUid(req)));
        req.setAttribute("title", "title.payments.edit");
        req.setAttribute("pageContent", "/pages/payments/paymentsEdit.jsp");
        req.setAttribute("save", API.Payments.EDIT);
        showModal(req, resp);
    }
}
