package utils;

import entity.RemovePack;
import entity.goal.Goal;
import entity.goal.GoalMember;
import entity.user.User;
import subscribe.Subscribe;
import subscribe.Subscriber;
import utils.json.JsonAble;

/**
 * Created by DELL on 09.07.2020.
 */
public class Updater {

    public void update(Subscribe subscribe, JsonAble jsonAble, User user) {
        System.out.println("Update " + subscribe + " for " + user);
        Subscriber subscriber = Subscriber.getInstance();
        if (subscriber != null){
            subscriber.send(subscribe, UpdateAction.update, jsonAble, user);
        } else {
            System.out.println("Subscriber is null");
        }
    }

    public void remove(Subscribe subscribe, int id, User user) {
        System.out.println("Remove " + subscribe + " for " + user);
        final Subscriber subscriber = Subscriber.getInstance();
        subscriber.send(subscribe, UpdateAction.remove, new RemovePack(id), user);
    }

    public void update(Goal goal) {
        update(Subscribe.goal, goal, goal.getOwner());
        for (GoalMember member : goal.getMembers()){
            update(Subscribe.goal, goal, member.getMember());
        }
    }
}
