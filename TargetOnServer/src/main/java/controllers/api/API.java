package controllers.api;

import controllers.Servlet;
import org.json.simple.JSONObject;
import utils.answers.Answer;
import utils.answers.ErrorAnswer;
import utils.answers.SuccessAnswer;
import utils.json.JsonAble;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static constants.Keys.ENCODING;

public abstract class API extends Servlet {

    public static final Answer SUCCESS_ANSWER = new SuccessAnswer();
    public static final Answer EMPTY_BODY = new ErrorAnswer("No req body");

    public void write(HttpServletResponse resp, JSONObject json) throws IOException {
        write(resp, json.toJSONString());
    }
    public void write(HttpServletResponse resp, String msg) throws IOException {
        resp.setCharacterEncoding(ENCODING);
        resp.getWriter().write(msg);
    }

    public void write(HttpServletResponse resp, JsonAble jsonAble) throws IOException {
        write(resp, jsonAble.toJson());
    }

}
