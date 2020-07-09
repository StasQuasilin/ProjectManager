package utils;

import entity.user.User;
import subscribe.Subscribe;
import subscribe.Subscriber;
import utils.json.JsonAble;

/**
 * Created by DELL on 09.07.2020.
 */
public class Updater {

    public void update(Subscribe subscribe, JsonAble jsonAble, User user) {
        Subscriber subscriber = Subscriber.getInstance();
        if (subscriber != null){
            subscriber.send(subscribe, UpdateAction.update, jsonAble, user);
        } else {
            System.out.println("Subscriber is null");
        }
    }
}
;