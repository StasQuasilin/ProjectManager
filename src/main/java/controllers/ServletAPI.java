package controllers;

import services.answers.ErrorAnswer;
import services.answers.SuccessAnswer;
import utils.JsonParser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.05.2019.
 */
public abstract class ServletAPI extends IServlet {

    static final String ENCODING = "UTF-8";
    public static final String SUCCESS = "SUCCESS";//JsonParser.toJson(new SuccessAnswer()).toJSONString();
    public static final String ERROR = "ERROR";//JsonParser.toJson(new ErrorAnswer()).toJSONString();

    public void write(HttpServletResponse resp, String text) throws IOException {
        resp.setCharacterEncoding(ENCODING);
        resp.getWriter().write(text);
    }
}
