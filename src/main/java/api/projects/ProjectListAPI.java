package api.projects;

import constants.API;
import entity.Project;
import org.json.simple.JSONArray;
import services.hibernate.Hibernator;
import utils.JsonParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(API.PROJECT.LIST)
public class ProjectListAPI extends HttpServlet {

    private static final Hibernator hibernator = Hibernator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uid = req.getSession().getAttribute("uid").toString();
        List<Project> projects = hibernator.query(Project.class, "creator", Integer.parseInt(uid));
        JSONArray array = projects.stream().map(JsonParser::toJson).collect(Collectors.toCollection(JSONArray::new));
        PostUtil.write(resp, array.toJSONString());
    }
}
