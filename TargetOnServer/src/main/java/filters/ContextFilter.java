package filters;

import entity.user.User;
import utils.db.hibernate.Hibernator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static constants.Keys.*;

@WebFilter(value = {ASTERISK})
public class ContextFilter implements Filter {

    private User user;

    @Override
    public void init(FilterConfig filterConfig) {
        Hibernator instance = Hibernator.getInstance();
        user = instance.get(User.class, ID, 1);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setAttribute(CONTEXT, request.getContextPath());
        request.setAttribute(LOCALE, "uk");
        request.getSession().setAttribute(USER, user);
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
