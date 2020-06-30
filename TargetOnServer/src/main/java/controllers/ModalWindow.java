package controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class ModalWindow extends HttpServlet {
    private static final String MODAL_WINDOW = "/pages/modalWindow.jsp";

    public void show(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(MODAL_WINDOW).forward(req, resp);
    }
}
