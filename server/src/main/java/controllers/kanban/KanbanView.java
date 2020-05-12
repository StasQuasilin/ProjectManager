package controllers.kanban;

import api.socket.Subscribe;
import constants.API;
import constants.Branches;
import controllers.IPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 27.02.2020.
 */
@WebServlet(Branches.KANBAN)
public class KanbanView extends IPage {
    private static final String _TITLE = "title.kanban";
    private static final String _CONTENT = "/pages/kanban/kanbanList.jsp";
    private static final String _FILTER = "/pages/kanban/kanbanFilter.jsp";
    private static final Subscribe[] subscribes = new Subscribe[]{Subscribe.kanban};
    private static final Subscribe[] projectSubscribe = new Subscribe[]{Subscribe.projects};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(PAGE_CONTENT, _CONTENT);
        req.setAttribute(SHOW, Branches.KANBAN_SHOW);
        req.setAttribute(SAVE, API.TASK_EDIT);
        req.setAttribute(CHANGE_STATUS, API.CHANGE_TASK_STATUS);
        req.setAttribute(REMOVE_TASK, Branches.REMOVE_TASK);
        req.setAttribute(SUBSCRIBES, subscribes);
        req.setAttribute(PROJECTS, projectSubscribe);
        req.setAttribute(FILTER, _FILTER);
        showPage(req, resp);
    }
}
