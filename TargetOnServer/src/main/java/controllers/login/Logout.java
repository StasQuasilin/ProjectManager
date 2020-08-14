package controllers.login;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.Page;
import controllers.Servlet;
import controllers.api.API;
import entity.user.User;
import subscribe.Subscriber;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet(ApiLinks.LOGOUT)
public class Logout extends API {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final User user = getUser(req);

        final Enumeration<String> sessionNames = req.getSession().getAttributeNames();
        while (sessionNames.hasMoreElements()){
            final String name = sessionNames.nextElement();
            req.getSession().removeAttribute(name);
        }

        final Enumeration<String> attributeNames = req.getAttributeNames();
        while (attributeNames.hasMoreElements()){
            final String name = attributeNames.nextElement();
            req.removeAttribute(name);
        }



        write(resp, req.getContextPath() + UrlLinks.LOGIN);

    }
}
