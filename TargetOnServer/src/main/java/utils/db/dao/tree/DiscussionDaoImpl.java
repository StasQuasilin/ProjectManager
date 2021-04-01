package utils.db.dao.tree;

import constants.Keys;
import entity.task.Task;
import entity.task.TaskDiscussion;
import utils.db.hibernate.Hibernator;

import java.util.List;

public class DiscussionDaoImpl implements DiscussionDao {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public TaskDiscussion getDiscussion(Object id) {
        return hibernator.get(TaskDiscussion.class, Keys.ID, id);
    }

    @Override
    public void saveDiscussion(TaskDiscussion discussion) {
        hibernator.save(discussion);
    }

    @Override
    public List<TaskDiscussion> getDiscussions(Task task) {
        return hibernator.query(TaskDiscussion.class, Keys.TASK, task);
    }
}
