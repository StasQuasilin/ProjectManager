package utils.tree;

import entity.finance.category.Header;
import entity.task.Task;
import org.json.simple.JSONObject;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

import java.util.List;

import static constants.Keys.CHILDREN;

public class TreeUtil {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public JSONObject buildTree(Task parent, Header header) {

        JSONObject jsonObject = parent != null ? parent.shortJson() : header.shortJson();

        final List<Task> children = taskDAO.getTasksByParent(header);

        JSONObject object = new JSONObject();
        for (Task task : children) {
            object.put(task.getId(), buildTree(task, task.getHeader()));
        }
        jsonObject.put(CHILDREN, object);
        return jsonObject;
    }

    public JSONObject buildTree(Header header) {
        final Task task = taskDAO.getTaskByHeader(header);
        return buildTree(task, header);
    }
}
