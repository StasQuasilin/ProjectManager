package filters;

import utils.db.hibernate.Hibernator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static constants.Keys.*;

@WebFilter(value = {ASTERISK})
public class ContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        Hibernator.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        request.setAttribute(CONTEXT, request.getContextPath());
        request.setAttribute(LOCALE, "uk");
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
