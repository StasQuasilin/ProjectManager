package controllers.api.tree;

import constants.ApiLinks;
import controllers.api.API;
import entity.finance.category.Header;
import entity.task.Task;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.db.dao.TitleDAO;
import utils.db.dao.category.CategoryDAO;
import utils.db.dao.daoService;
import utils.db.dao.goal.GoalDAO;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;
import utils.tree.TreeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static constants.Keys.CHILDREN;
import static constants.Keys.ID;
@WebServlet(ApiLinks.TREE_BUILDER)
public class TreeBuildAPI extends API {

    private final TitleDAO titleDAO = daoService.getTitleDAO();
    private final TreeUtil treeUtil = new TreeUtil();
    private final GoalDAO goalDAO = daoService.getGoalDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            Header root = titleDAO.getHeader(body.get(ID));
            write(resp, treeUtil.buildTree(root));
            final User user = getUser(req);
            goalDAO.changeActiveGoal(root, user);
        }
    }


}
