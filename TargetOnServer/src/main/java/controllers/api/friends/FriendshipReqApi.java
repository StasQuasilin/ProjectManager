package controllers.api.friends;

import constants.ApiLinks;
import constants.Keys;
import controllers.api.API;
import entity.social.FriendshipRequest;
import entity.user.User;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.db.dao.daoService;
import utils.db.dao.user.FriendshipDAO;
import utils.db.dao.user.UserDAO;
import utils.friends.FriendshipUtil;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ApiLinks.FRIENDSHIP_REQ)
public class FriendshipReqApi extends API {

    private final UserDAO userDAO = daoService.getUserDAO();
    private final FriendshipDAO friendshipDAO = daoService.getFriendshipDAO();
    private final FriendshipUtil friendshipUtil = new FriendshipUtil();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        Answer answer;
        if (body != null){
            answer = new SuccessAnswer();
            final User owner = getUser(req);
            final User user = userDAO.getUserById(body.get(Keys.USER));
            FriendshipRequest friendshipReq = friendshipDAO.getFriendshipReq(user, owner);
            if (friendshipReq == null){
                friendshipReq = friendshipDAO.getFriendshipReq(owner, user);
                if (friendshipReq == null){
                    friendshipReq = new FriendshipRequest();
                    friendshipReq.setOwner(owner);
                    friendshipReq.setFriend(user);
                    if (body.containKey(Keys.NOTE)){
                        friendshipReq.setNote(body.getString(Keys.NOTE));
                    }
                    friendshipUtil.makeFriendshipReq(friendshipReq);
                } else {
                    answer.addAttribute(Keys.MESSAGE, "Friendship request already exist");
                }
            } else {
                friendshipUtil.makeFriendship(friendshipReq);
            }
        } else {
            answer = new ErrorAnswer("Empty request body");
        }
        write(resp, answer);
    }
}
