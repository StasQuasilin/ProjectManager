package api.socket;

import api.socket.handlers.ISocketHandler;
import constants.Keys;
import entity.budget.Budget;
import entity.transactions.Transaction;
import entity.calendar.CalendarItem;
import entity.project.Project;
import entity.project.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonPool;

import java.io.IOException;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class UpdateUtil implements Keys {

    final SubscribeMaster subscribeMaster = SubscribeMaster.getMaster();
    JsonPool pool = JsonPool.getPool();

    public void onSave(Task task) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.calendar);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(task.toJson());
        object.put(UPDATE, array);
        handler.send(task.getOwner(), object);
    }

    public void onSave(Transaction transaction) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.transactions);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(transaction.toJson());
        object.put(UPDATE, array);
        handler.send(transaction.getBudget().getOwner(), object);
    }

    public void onSave(Budget budget) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.budget);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(budget.toJson());
        object.put(UPDATE, array);
        handler.send(budget.getOwner(), object);
    }

    public void onSave(Project project) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.projects);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        JSONObject jsonObject = project.toJson();
        jsonObject.put(ROLE, OWNER);
        array.add(jsonObject);
        object.put(UPDATE, array);
        handler.send(project.getOwner(), object);
    }

    public void onRemove(Project project) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.projects);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(project.toJson());
        object.put(REMOVE, array);
        handler.send(project.getOwner(), object);
    }

    public void onRemove(Task task) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.calendar);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(task.toJson());
        object.put(REMOVE, array);
        handler.send(task.getOwner(), object);
    }

    public void onSave(CalendarItem item) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.calendar);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(item.toJson());
        object.put(UPDATE, array);
        handler.send(item.getTask().getOwner(), object);
    }
}
