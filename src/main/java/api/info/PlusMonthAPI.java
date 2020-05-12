package api.info;

import constants.API;
import utils.DateParser;
import utils.PostUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Created by szpt_user045 on 25.02.2019.
 */
@WebServlet(API.INFO.ADD_MONTH)
public class PlusMonthAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, String> body = PostUtil.parseBody(req);
        LocalDate date = DateParser.parseFromEditor(body.get("date")).toLocalDate();
        int count = Integer.parseInt(body.get("count"));

        PostUtil.write(resp, DateParser.toString(Date.valueOf(date.plusMonths(count))));

    }
}
