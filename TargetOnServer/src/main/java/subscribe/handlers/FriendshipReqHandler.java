package subscribe.handlers;

import entity.user.User;
import subscribe.Subscribe;

public class FriendshipReqHandler extends SubscribeHandler {
    public FriendshipReqHandler() {
        super(Subscribe.friendshipReq);
    }

    @Override
    Object getItems(User user) {
        return null;
    }
}
