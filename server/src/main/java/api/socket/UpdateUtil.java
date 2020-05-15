package api.socket;

import api.socket.handlers.ISocketHandler;
import constants.Keys;
import entity.accounts.Account;
import entity.transactions.Transaction;
import entity.calendar.CalendarItem;
import entity.project.Project;
import entity.task.Task;
import entity.transactions.buy.list.BuyList;
import entity.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import utils.JsonAble;
import utils.JsonPool;

import java.io.IOException;

/**
 * Created by szpt_user045 on 17.02.2020.
 */
public class UpdateUtil implements Keys {

    final SubscribeMaster subscribeMaster = SubscribeMaster.getMaster();
    JsonPool pool = JsonPool.getPool();

    public void onSave(Task task) throws IOException {
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(task.toJson());
        object.put(UPDATE, array);
        subscribeMaster.getHandler(Subscribe.tree).send(task.getOwner(), object);
        subscribeMaster.getHandler(Subscribe.calendar).send(task.getOwner(), object);
        subscribeMaster.getHandler(Subscribe.kanban).send(task.getOwner(), object);
    }

    public void onSave(Transaction transaction) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.transactions);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(transaction.toJson());
        object.put(UPDATE, array);
        handler.send(transaction.getAccount().getOwner(), object);
    }

    public void onSave(Account account) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.accounts);
        JSONObject object = pool.getObject();
        JSONArray array = pool.getArray();
        array.add(account.toJson());
        object.put(UPDATE, array);
        handler.send(account.getOwner(), object);
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

    public void onSave(BuyList buyList) throws IOException {
        ISocketHandler handler = subscribeMaster.getHandler(Subscribe.buyList);
        send(handler, buyList, buyList.getOwner());
    }

    private void send(ISocketHandler handler, JsonAble jsonAble, User user) throws IOException {
        if (handler != null && user != null) {
            JSONObject object = pool.getObject();
            JSONArray array = pool.getArray();
            array.add(jsonAble.toJson());
            object.put(UPDATE, array);
            handler.send(user, object);
        }
    }
}
