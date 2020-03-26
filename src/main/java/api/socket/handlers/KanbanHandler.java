package api.socket.handlers;

import api.socket.Subscribe;
import entity.budget.Transaction;
import entity.project.Project;
import entity.project.Task;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;

public class KanbanHandler extends ISocketHandler {
    public KanbanHandler(Subscribe subscribe) {
        super(subscribe);
    }

    @Override
    public void onSubscribe(User user, Session session) throws IOException {
        JSONArray array = pool.getArray();
        ArrayList<Project> projects = new ArrayList<>();
        projects.addAll(dao.getProjectsByOwner(user));
        projects.addAll(dao.getProjectsByMembers(user));
        for (Project project : projects){
            for (Task task : dao.getTaskToDo(project.getTask())){
                array.add(task.toJson());
            }
        }
        JSONObject object = pool.getObject();
        object.put(UPDATE, array);
        send(session, object);
    }
}
