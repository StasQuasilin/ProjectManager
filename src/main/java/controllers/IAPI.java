package controllers;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by szpt_user045 on 24.05.2019.
 */
public abstract class IAPI {

    static final String ENCODING = "UTF-8";

    public void write(HttpServletResponse resp, String text) throws IOException {
        resp.setCharacterEncoding(ENCODING);
        resp.getWriter().write(text);
    }
}
