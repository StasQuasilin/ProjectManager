package api.socket.handlers;

import api.socket.Subscribe;
import entity.budget.Transaction;
import entity.project.Task;
import entity.project.TaskStatus;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.State;

import javax.websocket.Session;
import java.io.IOException;

public class TreeHandler extends ISocketHandler{
    public TreeHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();

        for (Task task : dao.getTaskByUserAndParent(user, null)){
            array.add(task.toJson());
        }

        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
