package controllers.finances.categories;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(UrlLinks.CATEGORY_PAGE)
public class CategoryListPage extends Page {
    private static final String _CONTENT = "/pages/finances/categories/categoryList.jsp";
    private static final String _TITLE = "title.categories";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Keys.TITLE, _TITLE);
        req.setAttribute(Keys.CONTENT, _CONTENT);
        req.setAttribute(Keys.GET_CATEGORIES, ApiLinks.GET_CATEGORIES);
        show(req, resp);
    }
}
