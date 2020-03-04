package api.socket.handlers;

import api.socket.Subscribe;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.State;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 18.02.2020.
 */
public class CalendarHandler extends ISocketHandler {
    public CalendarHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();
        for (Task task : dao.getTaskByUser(user, TaskStatus.progressing, State.notNull, State.isNull)){
            array.add(task.toJson());
        }
        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
