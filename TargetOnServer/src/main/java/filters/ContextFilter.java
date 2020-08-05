package filters;

import entity.user.User;
import utils.CalendarReplaceUtil;
import utils.db.hibernate.HibernateSessionFactory;
import utils.db.hibernate.Hibernator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static constants.Keys.*;

@WebFilter(value = {ASTERISK})
public class ContextFilter implements Filter {

    private User user;

    @Override
    public void init(FilterConfig filterConfig) {
        Hibernator instance = Hibernator.getInstance();
        user = instance.get(User.class, ID, 1);
        CalendarReplaceUtil.init();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        request.setAttribute(CONTEXT, request.getContextPath());

        //todo delete this
        final HttpSession session = request.getSession();
        if (session.getAttribute(USER) == null) {
            session.setAttribute(USER, user);
        }
        if (session.getAttribute(LOCALE) == null){
            session.setAttribute(LOCALE, "uk");
        }

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("X-Content-Type-Options", "nosniff");

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        HibernateSessionFactory.shutdown();
        CalendarReplaceUtil.shutdown();
    }
}
