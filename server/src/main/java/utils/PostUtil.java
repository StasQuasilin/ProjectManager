package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by szpt_user045 on 11.01.2019.
 */
public class PostUtil {

    private static final String ENCODE = "UTF-8";
    static Writer writer;
    public static HashMap<String,String> parseHeaders(HttpServletRequest req) {
        HashMap<String,String> result = new HashMap<>();

        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()){
            String key = headerNames.nextElement();
            result.put(key, req.getHeader(key));
        }

        return result;
    }

    public synchronized static HashMap<String, String> parseBody(HttpServletRequest req) throws IOException {
        HashMap<String,String> result = new HashMap<>();

        String collect = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String[] split = collect.split("&");

        for (String s : split){
            String[] sub = s.split("=");
            if (sub.length==2) {
                result.put(sub[0], sub[1]);
            } else {
                result.put(sub[0], null);
            }
        }
        return result;
    }


    public static void write(HttpServletResponse resp, String data) throws IOException {
        resp.setCharacterEncoding(ENCODE);
        writer = resp.getWriter();
        writer.write(data);
        writer.close();
    }
}
