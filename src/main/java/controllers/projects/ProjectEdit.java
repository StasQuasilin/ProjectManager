package controllers.projects;

import constants.Links;
import entity.Project;
import org.apache.log4j.Logger;
import services.hibernate.Hibernator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by quasilin on 24.02.2019.
 */
@WebServlet(Links.PROJECT_EDIT)
public class ProjectEdit extends HttpServlet {

    private static final Logger log = Logger.getLogger(ProjectEdit.class);
    public static final Hibernator hibernator = Hibernator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("project",hibernator.get(Project.class, "id", id));
            req.setAttribute("title", "project.edit");
        } catch (Exception ignored){
            req.setAttribute("title", "project.create");
        }
        req.setAttribute("currentPage", "/pages/home/home.jsp");
        req.setAttribute("pageContent", "/pages/projects/projectEdit.jsp");
        req.getRequestDispatcher("/base.jsp").forward(req, resp);



    }
}
