package filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by ZPT_USER on 20.07.2018.
 */
@WebFilter(value = {"*.j", "/api/*"})
public class LoginFilter implements Filter {

    private static final Logger log = Logger.getLogger(LoginFilter.class);
//    private static final Hibernator hibernator = Hibernator.getHibernator();

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
