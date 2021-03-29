package utils.tree;

import entity.finance.category.Header;
import entity.task.Task;
import entity.task.TaskStatistic;
import org.json.simple.JSONObject;
import utils.db.dao.daoService;
import utils.db.dao.tree.TaskDAO;

import java.util.List;

import static constants.Keys.CHILDREN;
import static constants.Keys.STATISTIC;

public class TreeUtil {

    private final TaskDAO taskDAO = daoService.getTaskDAO();

    public JSONObject buildTree(Task parent, Header header) {

        JSONObject jsonObject = parent != null ? parent.toJson() : header.shortJson();

        final List<Task> children = taskDAO.getTasksByParent(header);

        JSONObject object = new JSONObject();
        for (Task task : children) {
            final JSONObject obj = buildTree(task, task.getHeader());
            if(task.isDoneIfChildren()){
                final TaskStatistic statistic = taskDAO.getStatistic(task.getHeader().getId());
                if(statistic != null){
                    obj.put(STATISTIC, statistic.toJson());
                }
            }
            object.put(task.getId(), obj);
        }
        jsonObject.put(CHILDREN, object);
        return jsonObject;
    }

    public JSONObject buildTree(Header header) {
        final Task task = taskDAO.getTaskByHeader(header);
        return buildTree(task, header);
    }
}
