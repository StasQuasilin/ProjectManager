package controllers.api.tree.discussion;

import com.sun.xml.bind.v2.model.core.ID;
import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.task.Task;
import entity.task.TaskDiscussion;
import utils.answers.Answer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.tree.DiscussionDao;
import utils.db.dao.tree.TaskDAO;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@WebServlet(ApiLinks.DISCUSSION_EDIT)
public class EditDiscussionApi extends API {

    private final DiscussionDao discussionDao = daoService.getDiscussionDao();
    private final TaskDAO taskDAO = daoService.getTaskDAO();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            TaskDiscussion discussion = discussionDao.getDiscussion(body.get(Keys.ID));
            if(discussion == null){
                discussion = new TaskDiscussion();
                final Task task = taskDAO.getTask(body.get(Keys.TASK));
                discussion.setTask(task);
                discussion.setParent(discussionDao.getDiscussion(body.get(Keys.ID)));
                discussion.setAuthor(getUser(req));
                discussion.setTime(Timestamp.valueOf(LocalDateTime.now()));
            }
            discussion.setText(body.getString(Keys.TEXT));
            discussionDao.saveDiscussion(discussion);
            Answer answer = new SuccessAnswer();
            answer.addAttribute(Keys.RESULT, discussion);
            write(resp, answer);
        }
    }
}
