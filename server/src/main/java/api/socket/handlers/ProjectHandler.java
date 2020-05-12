package api.socket.handlers;

import api.socket.Subscribe;
import entity.project.Project;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import services.hibernate.dbDAO;
import services.hibernate.dbDAOService;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class ProjectHandler extends ISocketHandler {

    public ProjectHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();
        for(Project p : dao.getProjectsByOwner(user)){
            JSONObject json = p.toJson();
            json.put(ROLE, OWNER);
            array.add(json);
        }
        for (Project p : dao.getProjectsByMembers(user)){
            JSONObject json = p.toJson();
            json.put(ROLE, MEMBER);
            array.add(json);
        }
        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
