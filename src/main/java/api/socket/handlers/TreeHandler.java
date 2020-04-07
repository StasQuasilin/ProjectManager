package api.socket.handlers;

import api.socket.Subscribe;
import entity.project.Project;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;

public class TreeHandler extends ISocketHandler{
    public TreeHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();

        for (Project project : dao.getProjectsByOwner(user)){
            array.add(project.getTask().toJson());
        }

        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
