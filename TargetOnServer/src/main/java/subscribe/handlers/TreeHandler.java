package subscribe.handlers;

import entity.user.User;
import subscribe.Subscribe;

public class TreeHandler extends SubscribeHandler{

    public TreeHandler() {
        super(Subscribe.tree);
    }

    @Override
    Object getItems(User user) {
        return null;
    }
}
