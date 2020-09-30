package utils.db.dao.user;

import entity.social.FriendshipRequest;
import entity.user.User;

import java.util.List;

public interface FriendshipDAO {
    FriendshipRequest getFriendshipReq(User owner, User user);

    List<User> getFriends(User user);
}
