package utils.db.dao.user;

import entity.social.Friendship;
import entity.social.FriendshipRequest;
import entity.user.User;
import utils.db.hibernate.Hibernator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FriendshipDAOHibernate implements FriendshipDAO {

    private final Hibernator hibernator = Hibernator.getInstance();

    @Override
    public FriendshipRequest getFriendshipReq(User owner, User friend) {
        final HashMap<String, Object> params = new HashMap<>();
        params.put("owner", owner);
        params.put("friend", friend);
        return hibernator.get(FriendshipRequest.class, params);
    }

    @Override
    public List<User> getFriends(User user) {
        final LinkedList<User> friends = new LinkedList<>();
        for (Friendship friendship : hibernator.query(Friendship.class, "owner", user)){
            friends.add(friendship.getFriend());
        }
        return friends;
    }
}
