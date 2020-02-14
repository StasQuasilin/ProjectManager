package filters;

import constants.Keys;
import org.apache.log4j.Logger;
import services.hibernate.HibernateSessionFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Quasilin on 09.09.2018.
 */
@WebFilter(value = Keys.STAR)
public class ContextFilter implements Filter, Keys {

    public void init(FilterConfig filterConfig) throws ServletException {
        HibernateSessionFactory.init();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        req.setAttribute(CONTEXT, req.getContextPath());
        req.setCharacterEncoding(ENCODING);
        req.getSession().setAttribute(LANGUAGE, "uk");

        filterChain.doFilter(req, servletResponse);
    }

    public void destroy() {
        HibernateSessionFactory.shutdown();
    }
}
