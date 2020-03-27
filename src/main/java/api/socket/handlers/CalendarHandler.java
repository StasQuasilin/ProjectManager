package api.socket.handlers;

import api.socket.Subscribe;
import entity.calendar.CalendarItem;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.State;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by szpt_user045 on 18.02.2020.
 */
public class CalendarHandler extends ISocketHandler {

    public CalendarHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray tasks = pool.getArray();
        for (Task task : dao.getTaskByUser(user, TaskStatus.progressing, State.notNull, State.isNull)){
            tasks.add(task.toJson());
        }

        JSONArray items = pool.getArray();
        for (CalendarItem item : dao.getCalendarItems(Date.valueOf(LocalDate.now()))){
            items.add(item.toJson());
        }

        JSONObject object = pool.getObject();
        object.put(UPDATE, tasks);
        object.put(ITEMS, items);
        send(session, object);
    }
}
