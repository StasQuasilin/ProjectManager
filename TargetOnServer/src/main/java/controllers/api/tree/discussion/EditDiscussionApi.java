package controllers.api.tree.discussion;

import constants.ApiLinks;
import controllers.api.API;
import utils.answers.Answer;
import utils.json.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ApiLinks.DISCUSSION_EDIT)
public class EditDiscussionApi extends API {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final JsonObject body = parseBody(req);
        if (body != null){

        }
    }
}
