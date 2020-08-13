package filters;

import constants.UrlLinks;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static constants.ApiLinks.API;
import static constants.Keys.*;
import static constants.UrlLinks.HOME;
import static constants.UrlLinks.SUFFIX;

@WebFilter(value = {HOME, ASTERISK + SUFFIX, API + SLASH + ASTERISK})
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpSession session = request.getSession();
        final Object user = session.getAttribute(USER);
        if(user == null){
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.sendRedirect(request.getContextPath() + UrlLinks.LOGIN);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
