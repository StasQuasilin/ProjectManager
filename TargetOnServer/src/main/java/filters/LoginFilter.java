package filters;

import constants.UrlLinks;
import org.json.simple.JSONObject;
import utils.db.dao.daoService;
import utils.db.dao.user.UserDAO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import static constants.ApiLinks.API;
import static constants.Keys.*;
import static constants.UrlLinks.HOME;
import static constants.UrlLinks.SUFFIX;

@WebFilter(value = {HOME, ASTERISK + SUFFIX, API + SLASH + ASTERISK})
public class LoginFilter implements Filter {

    private final UserDAO userDAO = daoService.getUserDAO();
    private String errorAnswer;

    @Override
    public void init(FilterConfig filterConfig) {
        final HashMap<String, Object> param = new HashMap<>();
        param.put(STATUS, ERROR);
        param.put(REASON, UNAUTHORISED);
        errorAnswer = new JSONObject(param).toJSONString();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpSession session = request.getSession();
        Object user = session.getAttribute(USER);
        boolean apiAccess = false;
        if (user == null){
            final String header = request.getHeader(USER);
            if (header != null){
                apiAccess = true;
                System.out.println("TOKEN: " + header);
                user = userDAO.getUserById(header);
            }
        }
        if(user == null){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            if (apiAccess){
                response.getWriter().write(errorAnswer);
            } else {
                response.sendRedirect(request.getContextPath() + UrlLinks.LOGIN);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
