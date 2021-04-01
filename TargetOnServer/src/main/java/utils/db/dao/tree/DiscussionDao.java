package utils.db.dao.tree;

import entity.task.Task;
import entity.task.TaskDiscussion;

import java.util.List;

public interface DiscussionDao {
    TaskDiscussion getDiscussion(Object id);
    void saveDiscussion(TaskDiscussion discussion);

    List<TaskDiscussion> getDiscussions(Task task);
}
