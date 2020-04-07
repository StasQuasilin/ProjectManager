package api.calendar;

import api.socket.UpdateUtil;
import constants.API;
import controllers.ServletAPI;
import entity.calendar.CalendarItem;
import entity.task.Task;
import entity.task.TaskStatus;
import entity.task.TaskType;
import entity.user.User;
import org.json.simple.JSONObject;
import services.answers.SuccessAnswer;
import utils.TaskUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by szpt_user045 on 18.02.2020.
 */
@WebServlet(API.TASK_EDIT)
public class CalendarItemEditAPI extends ServletAPI {

    private final UpdateUtil updateUtil = new UpdateUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject body = parseBody(req);
        if (body != null){
            System.out.println(body);
            User user = getUser(req);

            CalendarItem calendarItem = dao.getObjectById(CalendarItem.class, body.get(ID));
            if(calendarItem == null){
                calendarItem = new CalendarItem();
            }

            Task task = calendarItem.getTask();
            //todo remove this to editor
            if (task == null){
                task = new Task();
                task.setOwner(user);
                task.setStatus(TaskStatus.progressing);
                task.setType(TaskType.once);
                calendarItem.setTask(task);
            }

            String title = String.valueOf(body.get(TITLE));
            task.setTitle(title);

            Task parent = null;
            if (body.containsKey(PARENT)) {
                JSONObject p = (JSONObject) body.get(PARENT);
                parent = dao.getObjectById(Task.class, p.get(ID));
                if(parent == null){
                    parent = new Task();
                    parent.setTitle(String.valueOf(p.get(TITLE)));
                    parent.setOwner(user);
                    parent.setStatus(TaskStatus.active);
                    dao.save(parent);
                }
                task.setParent(parent);
            }

            dao.save(task);

            Timestamp date = Timestamp.valueOf(String.valueOf(body.get(DATE)));
            calendarItem.setBegin(date);
            Timestamp end = Timestamp.valueOf(String.valueOf(body.get(END)));
            System.out.println(end.toString());
            calendarItem.setEnd(end);
            dao.save(calendarItem);

            JSONObject json = new SuccessAnswer(RESULT, calendarItem.getId()).toJson();
            write(resp, json.toJSONString());
            pool.put(json);

            if (parent != null) {
                TaskUtil.checkParenthood(parent, user);
            }

            updateUtil.onSave(calendarItem);
        }
    }
}
