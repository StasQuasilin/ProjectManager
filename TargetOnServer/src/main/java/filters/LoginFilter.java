package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import static constants.ApiLinks.API;
import static constants.Keys.ASTERISK;
import static constants.Keys.SLASH;
import static constants.UrlLinks.HOME;
import static constants.UrlLinks.SUFFIX;

@WebFilter(value = {HOME, ASTERISK + SUFFIX, API + SLASH + ASTERISK})
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
