package filters;

import constants.Branches;
import entity.user.UserAccess;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by ZPT_USER on 20.07.2018.
 */
//@WebFilter(value = {"*.j", "/api/*"})
public class LoginFilter implements Filter {

    private static final String TOKEN = "token";

    private static final Logger log = Logger.getLogger(LoginFilter.class);
//    private static final Hibernator hibernator = Hibernator.getHibernator();
    private static final HashMap<String, UserAccess> activeUsers = new HashMap<>();

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String token = (String) request.getSession().getAttribute(TOKEN);
        if (token != null && activeUsers.containsKey(token)){
            request.getSession().setAttribute(TOKEN, addUser(activeUsers.remove(token)));
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(request.getContextPath() + Branches.LOGIN);
        }

    }

    public static String addUser(UserAccess userAccess){
        String token = getToken();
        activeUsers.put(token, userAccess);
        return token;
    }

    private static String getToken() {
        String uuid = UUID.randomUUID().toString();
        if (activeUsers.containsKey(uuid)){
            return getToken();
        }
        return uuid;
    }

    @Override
    public void destroy() {

    }
}
