package controllers.friends;

import constants.ApiLinks;
import constants.Keys;
import constants.UrlLinks;
import controllers.Page;
import subscribe.Subscribe;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(UrlLinks.FRIENDS)
public class FriendsList extends Page {
    private static final String _TITLE = "title.friends";
    private static final String _CONTENT = "/pages/friends/friendList.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(Keys.TITLE, _TITLE);
        req.setAttribute(Keys.CONTENT, _CONTENT);
        req.setAttribute(Keys.FIND_USER, ApiLinks.FIND_USER);
        req.setAttribute(Keys.FRIENDS_SUBSCRIBE, Subscribe.friends);
        req.setAttribute(Keys.FRIENDSHIP_REQ_SUBSCRIBE, Subscribe.friendshipReq);
        show(req, resp);
    }
}
