package subscribe.handlers;

import entity.user.User;
import org.json.simple.JSONArray;
import subscribe.Subscribe;
import utils.db.dao.daoService;
import utils.db.dao.user.FriendshipDAO;

public class FriendsHandler extends SubscribeHandler {

    private final FriendshipDAO friendshipDAO = daoService.getFriendshipDAO();

    public FriendsHandler() {
        super(Subscribe.friends);
    }

    @Override
    public Object getItems(User user) {
        final JSONArray array = new JSONArray();

        for (User friend : friendshipDAO.getFriends(user)){
            array.add(friend.toJson());
        }

        return array;
    }
}
