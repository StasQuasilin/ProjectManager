package controllers.tree;

import constants.ApiLinks;
import constants.UrlLinks;
import controllers.ModalWindow;
import entity.finance.buy.BuyList;
import entity.finance.buy.BuyListItem;
import entity.finance.category.Category;
import entity.finance.category.Header;
import entity.goal.Goal;
import entity.task.Task;
import entity.task.TaskStatus;
import utils.db.dao.TitleDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.finance.buy.BuyListDAO;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.*;

@WebServlet(UrlLinks.TASK_EDIT)
public class TaskEdit extends ModalWindow {
    private static final String _TITLE = "title.task.edit";
    private static final String _CONTENT = "/pages/tree/taskEdit.jsp";

    private final GoalDAO goalDAO = daoService.getGoalDAO();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    private final CategoryDAO categoryDAO = daoService.getCategoryDAO();
    private final BuyListDAO buyListDAO = daoService.getBuyListDAO();
    private final TitleDAO titleDAO = daoService.getTitleDAO();
    private final TaskStatus[] statuses = {TaskStatus.active, TaskStatus.progressing, TaskStatus.done};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            final Task task = taskDAO.getTask(body.get(ID));

            Header parent = null;
            BuyList buyList = null;

            if (task != null){
                req.setAttribute(TASK, task);
                final Header header = task.getHeader();
//                final BuyListItem buyListItem = buyListDAO.getItemByCategory(category);
//                if (buyListItem != null) {
//                    buyList = buyListItem.getList();
//                    req.setAttribute(BUY_LIST, buyList);
//                }
                parent = header.getParent();
            } else if (body.containKey(PARENT)){
                parent = titleDAO.getHeader(body.get(PARENT));
            }

            req.setAttribute(PARENT, parent);

            if (buyList == null && parent != null){
//                Category root = parent.getParent();
//                if (root != null) {
//                    while (root.getParent() != null) {
//                        root = root.getParent();
//                    }
//                } else {
//                    root = parent;
//                }
//                final Goal goal = goalDAO.getGoalByCategory(root);
//                final int buyListId = goal.getBuyList();
//                if (buyListId > 0){
//                    final BuyList list = buyListDAO.getList(buyListId);
//                    req.setAttribute(ROOT_BUY_LIST, list);
//                }
            }
            req.setAttribute(DEPENDENT, taskDAO.getDependency(task));
            req.setAttribute(PRINCIPAL, taskDAO.getPrincipal(task));
        }
        req.setAttribute(TITLE, _TITLE);
        req.setAttribute(CONTENT, _CONTENT);
        req.setAttribute(STATUS, statuses);
        req.setAttribute(FIND_CATEGORY, ApiLinks.FIND_CATEGORY);
        req.setAttribute(FIND_BUY_LIST, ApiLinks.FIND_BUY_LIST);
        req.setAttribute(SAVE, ApiLinks.TASK_EDIT);
        show(req, resp);
    }
}
